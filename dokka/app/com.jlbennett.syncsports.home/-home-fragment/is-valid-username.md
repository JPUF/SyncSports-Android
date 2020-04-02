[app](../../index.md) / [com.jlbennett.syncsports.home](../index.md) / [HomeFragment](index.md) / [isValidUsername](./is-valid-username.md)

# isValidUsername

`private fun isValidUsername(username: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Tests if the username is appropriate for use.

Valid names are those that do not contain special characters (so only digits and letters), are between 3 and 16 characters in length, and do not contain spaces.

### Parameters

`username` - name to be tested

**Return**
True if valid, else false.

