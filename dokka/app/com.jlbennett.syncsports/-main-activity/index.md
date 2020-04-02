[app](../../index.md) / [com.jlbennett.syncsports](../index.md) / [MainActivity](./index.md)

# MainActivity

`class MainActivity : AppCompatActivity`

The underlying Activity class.

This activity hosts all other fragments, which are responsible for displaying each individual screen. As can be seen in activity_main.xml, this MainActivity simply has a singular 'NavHostFragment' which controls the currently visible Fragment, via the Navigation component.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | The underlying Activity class.`MainActivity()` |

### Functions

| Name | Summary |
|---|---|
| [onCreate](on-create.md) | `fun onCreate(savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
