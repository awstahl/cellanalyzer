cellanalyzer
============

An app to detect when a device connects to a non-carrier cell tower. Intended for use on the Blackphone.

Work in progress.


Features
============

Intend to do the following:

1. Monitor cell info for changes
2. Consult FCC cell tower DB for current cell(s)
3. Generate alert if cell tower is not found (i.e. appears to be illegitimate)


Current State/TODO's
=============

Blockers:
- Blackphone's PrivateOS appears to have limited or restricted support for the android.telephony API
- Calling getAllCellInfo() appears to always return null, but this needs further testing
- Calling getNeighboringCellInfo() appears to work, but doesn't provide data w/ clear FCC DB correlation
-- Need to research using location data
- How to retrieve CellInfo{,Gsm,Cdma,Lte} objects?
-- Right now using TelephonyManager

General To-Do:
- Fix up cell info UI
- Implement cell tower provider DB (via sqlite or REST?)
- Implement broadcaster
-- Register/listen for cellChange events
-- Notify when new cell not in DB
- Settings?
