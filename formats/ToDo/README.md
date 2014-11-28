# JSON with GSON
This project contains a demonstration of [GSON](https://code.google.com/p/google-gson/). Gson is a Java library that can be used to convert arbitrary Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.

The following examples are included:
* __AddPerson__ (```gradle -q addPerson```) tests first it the file ```address_book.json``` exists. If it exists, the example unmarshalls its content into Java objects. Example code expects that the file contains an ```AddressBook``` instance. Then, code asks to the user the details of a ```Person``` and then the person is added to the address book. Finally, the address book is marshalled into a JSON file with name ```address_book.json```.
* __ListPeople__ (```gradle -q listPeople```) unmarshalls ```address_book.json``` into Java objects and then dump the objects to the console.
