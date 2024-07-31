# Store2

### Use
~~~ kotlin
// build.gradle.kts
dependencies {
    implementation("io.github.xiaobaicz:store2:1.4.1")
    // or
    implementation("io.github.xiaobaicz:store2-saver-mmap:1.4.1")
    // or
    implementation("io.github.xiaobaicz:store2-saver-mmkv:1.4.1")
}
~~~

~~~ kotlin
// get store instance
val store = store<Table>()
val table by store

// Declaration table interface
interface Table {
    companion object : Store<Local> by store, Local by table

    // Declaration table field (any type)
    var log: Boolean
    
    // default value
    @StringDef("1.0")
    var version: String
}
~~~

~~~ kotlin
fun main() {
    // store: has/get/set/remove/clear
    Table.has(Table::log)           // log exist?
    Table.get(Table::log)           // get value
    Table.set(Table::log, true)     // set value
    Table.remove(Table::log)        // remove log
    Table.clear()                   // clear table
    
    // get/set quick operations
    // table: get/set
    Table.log                       // get value
    Table.log = true                // set value
}
~~~