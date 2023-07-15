package com.sas.database

import com.sas.models.Customer
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI
import kotlin.reflect.KClass


object DdbProvider {
    private val ddb = DynamoDbClient.builder()
        .endpointOverride(URI.create("http://localhost:8000"))
        .region(Region.AP_SOUTH_1)
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create("fakeId", "fakeKey")
            )
        )
        .build()

    private val ddbE = DynamoDbEnhancedClient.builder()
        .dynamoDbClient(ddb)
        .build()

    private val customerDdbSchema = TableSchema.fromBean(Customer::class.java)
    private val customerDdbTable = ddbE.table("customers", customerDdbSchema)
//    customerDdbTable.createTable()

    private fun <T : Any> getDdbTable(clazz: KClass<T>): DynamoDbTable<T> {
        return when (clazz) {
            Customer::class -> customerDdbTable as DynamoDbTable<T>
            else -> throw IllegalArgumentException("Unknown DDb Class ${clazz.qualifiedName}")
        }
    }

    fun <T : Any> getItem(pk: String, clazz: KClass<T>): T? {
        val key = Key.builder().partitionValue(pk).build()
        val table = getDdbTable(clazz)
        return table.getItem(key)
    }

    fun <T : Any> putItem(item: T, clazz: KClass<T>): Unit {
        val table = getDdbTable(clazz)
        table.putItem(item)
    }
}
