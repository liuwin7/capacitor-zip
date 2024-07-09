# capacitor-zip [(npm)](https://www.npmjs.com/package/capacitor-zip-plugin)



Work in progress. Zip and Unzip files on android. Based on Zip4j library. Zip functionnality still not implemented only unzip works for now. Tested on capacitor v3, not tested on older capacitor versions.

## Install

```bash
npm install capacitor-zip
npx cap sync
```

## Android permissions
Make sure to add the following permissions in the application android manifest.xml
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

## API

<docgen-index>

* [`unzip(...)`](#unzip)
* [`addListener('onProgress', ...)`](#addlisteneronprogress)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### unzip(...)

```typescript
unzip(options: UnzipOptions) => Promise<UnzipResult>
```

unzip a zip file

| Param         | Type                                                  | Description   |
| ------------- | ----------------------------------------------------- | ------------- |
| **`options`** | <code><a href="#unzipoptions">UnzipOptions</a></code> | unzip options |

**Returns:** <code>Promise&lt;<a href="#unzipresult">UnzipResult</a>&gt;</code>

--------------------


### addListener('onProgress', ...)

```typescript
addListener(eventName: 'onProgress', listenerFunc: ProgressHandler) => Promise<PluginListenerHandle>
```

Listens for screen orientation changes.

| Param              | Type                                                        | Description       |
| ------------------ | ----------------------------------------------------------- | ----------------- |
| **`eventName`**    | <code>'onProgress'</code>                                   | event name        |
| **`listenerFunc`** | <code><a href="#progresshandler">ProgressHandler</a></code> | listener function |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### Interfaces


#### UnzipResult

| Prop          | Type                |
| ------------- | ------------------- |
| **`message`** | <code>string</code> |
| **`uri`**     | <code>string</code> |


#### UnzipOptions

| Prop              | Type                |
| ----------------- | ------------------- |
| **`source`**      | <code>string</code> |
| **`destination`** | <code>string</code> |
| **`password`**    | <code>string</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### Progress

<a href="#progress">Progress</a> object

| Prop           | Type                |
| -------------- | ------------------- |
| **`progress`** | <code>number</code> |
| **`current`**  | <code>string</code> |
| **`task`**     | <code>string</code> |


### Type Aliases


#### ProgressHandler

<a href="#progress">Progress</a> handler

<code>(progress: <a href="#progress">Progress</a>): void</code>

</docgen-api>
