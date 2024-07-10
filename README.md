# Store2

### Use
~~~ kotlin
// build.gradle.kts
dependencies {
    implementation("io.github.xiaobaicz:store2:1.3")
    // or
    implementation("io.github.xiaobaicz:store2-saver-mmap:1.3")
}
~~~

~~~ kotlin
// Declaration table interface
interface Table {

    // Declaration table field (any type)
    var log: Boolean
    
    // default value
    @StringDef("1.0")
    var version: String
    
}
~~~

~~~ kotlin
// new store factory
val factory = Store.Factory()

// get store instance
val store = factory.get<Table>()

// get table
val table by store

fun main() {
    // store: has/get/set/remove/clear
    store.has(Table::log)           // log exist?
    store.get(Table::log)           // get value
    store.set(Table::log, true)     // set value
    store.remove(Table::log)        // remove log
    store.clear()                   // clear table
    
    // get/set quick operations
    // table: get/set
    table.log                       // get value
    table.log = true                // set value
}
~~~