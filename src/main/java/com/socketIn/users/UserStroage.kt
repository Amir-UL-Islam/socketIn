package com.socketIn.users

class UserStorage private constructor() {
    private val users: MutableSet<String>

    init { // The code inside the init block is the first to be executed when the class is instantiated
        users = HashSet()
    }

    fun getUsers(): Set<String> {
        return users
    }

    @Throws(Exception::class)
    fun setUser(userName: String) {
        if (users.contains(userName)) {
            throw Exception("User already exists with userName: $userName")
        }
        users.add(userName)
    }

    companion object { // Companion Objects are is a powerful upgrade to static nested classes. In terms of functionality, they are equivalent to singleton static nested classes.
        // Companion type is not bound to an instance of the outer class, but to the outer class itself (so it’s not Outer().Nested() but Outer.Nested()), they can see and work with private members of the Outer class, they can inherit from superclasses, they implement interfaces, we can pass instances of the nested class around.
        @get:Synchronized // The synchronized keyword in java helps us reduce the concurrency when we share the same resource between multiple threads or processes. The fundamental features of the synchronized keyword are: The synchronized keyword locks the resources to a thread so that no other thread can access it at a time
        var instance: UserStorage? = null
            get() {
                if (field == null) {
                    field = UserStorage()
                }
                return field
            }
            private set
    }
}

