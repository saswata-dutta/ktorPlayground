package com.sas.models

import kotlinx.serialization.Serializable
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@Serializable
@DynamoDbBean
data class Customer(
    @get:DynamoDbPartitionKey var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null
)
