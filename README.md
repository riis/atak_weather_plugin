(Plugin Name)


_________________________________________________________________
PURPOSE AND CAPABILITIES

(General Description)


_________________________________________________________________
STATUS

(In Progress?  Expected release?  Released?  To Who?  When?)

_________________________________________________________________
POINT OF CONTACTS

(Who is developing this)

_________________________________________________________________
PORTS REQUIRED

(This is important for ATO, networking, and other security concerns)

_________________________________________________________________
EQUIPMENT REQUIRED

_________________________________________________________________
EQUIPMENT SUPPORTED

_________________________________________________________________
COMPILATION

_________________________________________________________________
DEVELOPER NOTES

To compile, you will need the appropriate signing keys and have your `local.properties` configured to use them. The following should be added to `local.properties` with the appropriate values

```yml
# Debug Keystore
takDebugKeyFile=<full file path>
takDebugKeyFilePassword=<password>
takDebugKeyAlias=<alias>
takDebugKeyPassword=<password>

# Release Keystore
takReleaseKeyFile=/Users/ljustice/workspace/atak-civ/keystore/debug.keystore
takReleaseKeyFilePassword=
takReleaseKeyAlias=
takReleaseKeyPassword=
```