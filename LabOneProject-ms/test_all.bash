#!/usr/bin/env bash

#
# Sample usage:
#   ./test_all.bash start stop
#   start and stop are optional
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
: ${HOST=localhost}
: ${PORT=8080}

#array to hold all our test data ids
allTestInventoryIds=()
allTestSneakerIds=()
allTestCustomerIds=()
allTestOrderIds=()

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

#have all the microservices come up yet?
function testUrl() {
    url=$@
    if curl $url -ks -f -o /dev/null
    then
          echo "Ok"
          return 0
    else
          echo -n "not yet"
          return 1
    fi;
}

#prepare the test data that will be passed in the curl commands for posts and puts
function setupTestdata() {


##CREATE SOME Inventory TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
#
body=\
'{
      "inventoryId": "011"


     }'
    recreateInventory 1 "$body"


##CREATE SOME Sneaker TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
body=\
'
{
      "model": "Nike 270",
              "price": "100",
              "size": 8,
              "color": "white and blue",
              "releaseYear": "2019",
              "availableStore": "Nike",
              "description": "build with high quality materials",
              "type": "sneaker"


        }'

    recreateSneaker 1 "$body"


##Create SOME Customers DATA
body=\
'
 {


               "customerFName": "John",
               "customerLName": "Doe",
               "number": "313-321-312",
               "email": "email@gmail.com",
               "customerPreferredSneaker": "nike air max plus",
               "customerPreferredBrand":"nike",
               "customerPreferredSize": "11",
               "customerStatus": "AVAILABLE"
             }'
      recreateCustomer 1 "$body"


##Create SOME Order DATA
body=\
'{

            "sneakerId": "040",
                "toSneakerId": "gw2334",
                "customerId": "001",
                "inventoryId": "011",
                "quantityBought": "2",
                "shippingAddress": [
                            {
                                 "street": "1180 RUE DOLLARD",
                                 "city": "Longueil",
                                 "state": "QC",
                                 "country: "CANADA",
                                 "postalCode": "J4K 4M7"
                            }
                        ],

                "orderStatus": "OrderStatus.PAID",
                "model": "Nike 270",
                "price": "100",
                "size": 8,
                "color": "white and blue",
                "customerFName": "John",
                "customerLName": "Doe",
                "number": "313-321-312",
                "email": "email@gmail.com"


}'
    recreateOrder 1 "$body" "001"



} #end of setupTestdata


#USING inventory TEST DATA - EXECUTE POST REQUEST
function recreateInventory() {
    local testId=$1
    local aggregate=$2


    inventoryId=$(curl -X POST http://$HOST:$PORT/api/v1/inventories -H "Content-Type:
    application/json" --data "$aggregate" | jq '.inventoryId')
    allTestInventoryIds[$testId]=$inventoryId
    echo "Added Inventory with inventoryId: ${allTestInventoryIds[$testId]}"
}

#USING sneaker TEST DATA - EXECUTE POST REQUEST
function recreateSneaker() {
    local testId=$1
    local aggregate=$2

    #create the sneaker and record the generated sneakerId
    sneakerId=$(curl -X POST http://$HOST:$PORT/api/v1/sneakers -H "Content-Type:
    application/json" --data "$aggregate" | jq '.sneakerId')
    allTestSneakerIds[$testId]=$sneakerId  # Fix here
    echo "Added Sneaker with sneakerId: ${allTestSneakerIds[$testId]}"
}


#USING customer TEST DATA - EXECUTE POST REQUEST
function recreateCustomer() {
    local testId=$1
    local aggregate=$2

    #create the customer and record the generated customerId
    customerId=$(curl -X POST http://$HOST:$PORT/api/v1/customers -H "Content-Type: application/json" --data "$aggregate" | jq '.customerId')
    allTestCustomerIds[$testId]=$customerId
    echo "Added Customer with customerId: ${allTestCustomerIds[$testId]}"
}



#USING customer TEST DATA - EXECUTE POST REQUEST
#USING customer TEST DATA - EXECUTE POST REQUEST
function recreateOrder() {
    local testId=$1
    local aggregate=$2
    local customerId=$3

    # Manually set the orderid
    local orderId="02322"

    # You can then use this orderId in your curl command
    curl -X POST http://$HOST:$PORT/api/v1/customers/"$customerId"/orders -H "Content-Type: application/json" --data "$aggregate" | jq -r '.orderId'

    allTestOrderIds[$testId]=$OrderId
    echo "Added Order with orderId: ${allTestOrderIds[$testId]}"
}







#don't start testing until all the microservices are up and running
function waitForService() {
    url=$@
    echo -n "Wait for: $url... "
    n=0
    until testUrl $url
    do
        n=$((n + 1))
        if [[ $n == 100 ]]
        then
            echo " Give up"
            exit 1
        else
            sleep 6
            echo -n ", retry #$n "
        fi
    done
}

#start of test script
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker-compose down"
    docker-compose down
    echo "$ docker-compose up -d"
    docker-compose up -d
fi

#try to delete an entity/aggregate that you've set up but that you don't need. This will confirm that things are working
waitForService curl -X DELETE http://$HOST:$PORT/api/v1/customers/001

setupTestdata

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
##verify that a get all customers works
echo -e "\nTest 1: Verify that a get all customers works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers -s"
assertEqual 10 $(echo $RESPONSE | jq ". | length")
#
#
## Verify that a normal get by id of earlier posted customer works
echo -e "\nTest 2: Verify that a normal get by id of earlier posted sneaker works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/sneakers/${allTestSneakerIds[1]} -s"
assertEqual ${allTestSneakerIds[1]} $(echo $RESPONSE | jq .sneakerId)
#assertEqual "\"Nike 270\"" $(echo $RESPONSE | jq ".model")
#
#
## Verify that an update of an earlier posted customer works - put at api-gateway has no response body
echo -e "\nTest 3: Verify that an update of an earlier posted customer works"
body=\
'{
                    "customerFName": "John",
                    "customerLName": "Doe",
                    "number": "313-321-312",
                    "email": "email@gmail.com",
                    "customerPreferredSneaker": "nike air max plus",
                    "customerPreferredBrand":"nike",
                    "customerPreferredSize": "11",
                    "customerStatus": "AVAILABLE"
     }'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#
#
## Verify that a delete of an earlier posted customer works
echo -e "\nTest 4: Verify that a delete of an earlier posted customer works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -s"
#
#
## Verify that a 404 (Not Found) status is returned for a non existing customerId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get customer request with a non existing customerId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customers/051 -s"
#
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid customerId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 6: Verify that a 422 (Unprocessable Entity) status is returned for a get customer request with an invalid customerId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customer/051 -s"



## sneaker Testing

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
##verify that a get all customers works
echo -e "\nTest 1: Verify that a get all sneakers works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/sneakers -s"
assertEqual 11 $(echo $RESPONSE | jq ". | length")
#
#
## Verify that a normal get by id of earlier posted customer works
## Verify that a normal get by id of earlier posted teams works
echo -e "\nTest 2: Verify that a normal get by id of earlier posted teams works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/sneakers/${allTestSneakerIds[1]} -s"
assertEqual ${allTestSneakerIds[1]} $(echo $RESPONSE | jq .sneakerId)


#
#
## Verify that an update of an earlier posted customer works - put at api-gateway has no response body
echo -e "\nTest 3: Verify that an update of an earlier posted sneaker works"
body=\
'
{
              "model": "Nike 270",
              "price": "100",
              "size": 8,
              "color": "white and blue"
        }'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/sneakers/${allTestSneakerIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#
#
## Verify that a delete of an earlier posted customer works
echo -e "\nTest 4: Verify that a delete of an earlier posted team works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/sneakers/${allTestSneakerIds[1]} -s"
#
#
## Verify that a 404 (Not Found) status is returned for a non existing customerId (c3540a89-cb47-4c96-888e-ff96708db4d7)
## Verify that a 404 (Not Found) status is returned for a non existing teamId
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get team request with a non existing teamId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/sneakers/011 -s -w \"%{http_code}\""

#
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid customerId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 6: Verify that a 422 (Unprocessable Entity) status is returned for a get team request with an invalid teamId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/sneakers/050"



## customer Testing

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
##verify that a get all customers works
echo -e "\nTest 1: Verify that a get all Customers works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers -s"
assertEqual 11 $(echo $RESPONSE | jq ". | length")
#
#
## Verify that a normal get by id of earlier posted customer works
## Verify that a normal get by id of earlier posted teams works
echo -e "\nTest 2: Verify that a normal get by id of earlier posted customer works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -s"
assertEqual ${allTestCustomerIds[1]} $(echo $RESPONSE | jq .customerId)
assertEqual "001" $(echo $RESPONSE | jq ".customerId")  # Fix here

#
#
## Verify that an update of an earlier posted customer works - put at api-gateway has no response body
echo -e "\nTest 3: Verify that an update of an earlier posted players works"
body=\
'{
       "customerFName": "John",
                           "customerLName": "Doe",
                           "number": "313-321-312",
                           "email": "email@gmail.com",
                           "customerPreferredSneaker": "nike air max plus",
                           "customerPreferredBrand":"nike",
                           "customerPreferredSize": "11",
                           "customerStatus": "CustomerStatus.AVAILABLE"
    }'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#
#
## Verify that a delete of an earlier posted customer works
echo -e "\nTest 4: Verify that a delete of an earlier posted players works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -s"
#
#
## Verify that a 404 (Not Found) status is returned for a non existing customerId (c3540a89-cb47-4c96-888e-ff96708db4d7)
## Verify that a 404 (Not Found) status is returned for a non existing teamId
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get player request with a non existing teamId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customers/001 -s -w \"%{http_code}\""

#
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid customerId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 6: Verify that a 422 (Unprocessable Entity) status is returned for a get player request with an invalid teamId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customerId/020 -s"


## orders Testing

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
##verify that a get all customers works
echo -e "\nTest 1: Verify that a get all order works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers/001/orders -s"
assertEqual 1 $(echo $RESPONSE | jq ". | length")
#
#
## Verify that a normal get by id of earlier posted customer works
## Verify that a normal get by id of earlier posted teams works
echo "Order ID: ${allTestOrderIds[1]}"
echo -e "\nTest 2: Verify that a normal get by id of earlier posted transfer works"
assertCurl 200 "curl http://localhost:8080/api/v1/customers/001/orders/${allTestOrderIds[1]} -s  -w \"%{http_code}\""
assertEqual ${allTestOrderIds[1]} $(echo $RESPONSE | jq .orderId)


#
#
## Verify that an update of an earlier posted customer works - put at api-gateway has no response body
echo -e "\nTest 3: Verify that an update of an earlier posted transfer works"
body=\
'{
    "sneakerId": "040",
                    "toSneakerId": "gw2334",
                    "inventoryId":  "011",
                    "customerId": "001",
                    "quantityBought": "2",
                    "shippingAddress": [
                                {
                                     "street": "1180 RUE DOLLARD",
                                     "city": "Longueil",
                                     "state": "QC",
                                     "country: "CANADA",
                                     "postalCode": "J4K 4M7"
                                }
                            ],

                    "orderStatus": "OrderStatus.PAID",
                    "model": "Nike 270",
                    "price": "100",
                    "size": 8,
                    "color": "white and blue",
                    "customerFName": "John",
                    "customerLName": "Doe",
                    "number": "313-321-312",
                    "email": "email@gmail.com"
}'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/customers/001/orders/${allTestOrderIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#
#
## Verify that a delete of an earlier posted customer works
echo -e "\nTest 4: Verify that a delete of an earlier posted transfer works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/customers/001/orders/${allTestOrderIds[1]} -s"
#
#
## Verify that a 404 (Not Found) status is returned for a non existing customerId (c3540a89-cb47-4c96-888e-ff96708db4d7)
## Verify that a 404 (Not Found) status is returned for a non existing teamId
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get transfer request with a non existing teamId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customers/001/orders/0200 -s -w \"%{http_code}\""

#
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid customerId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 6: Verify that a 422 (Unprocessable Entity) status is returned for a get transfer request with an invalid teamId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customers/001/orders/050"


if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi