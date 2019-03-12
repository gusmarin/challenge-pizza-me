# challenge-pizza-me
Coding challenge for application that displays closest pizza locations

Topics:
* Use Cases
* Class diagram
* Architecture
* Notes

1. USE CASES
The user is able to search nearby Pizza places based on current location
The user is able to see a detail view of each place where they can perform
   1. Call phone number
   2. Open address in map
   
2. CLASS DIAGRAM

MainActivity -> PlaceViewModel -> IDataRepository -> IDataImpl

Observer    <- PlaceViewModel <- IDataCallback <-  ICallbackImpl
                
3. ARCHITECTURE

The reason for the above architecture is to allow flexibility on the type of 
repository, data and use cases that this application will need as it grows.

Main entity is defined as a place and my main use case is to search nearby pizza, 
but this can be easily extended to support different places and different repos.
No matter what type of data implementation we are using UI will always expect a Place
making UI flexible.

Place View Model is the one holding and driving the dependencies and implementation
of underlaying layer.

DetailActivity can also be used to host an access to IDataRepository and have a
different set of APIs not yet implemented. 

4.NOTES

Pending Items on the scope on this challenge:
 - Refactor PlaceViewModel and create a UI mediator (non android) which
   will handle IDataRepository and make use of PlaceViewModel (for android specific 
   lifecycle awereness) but with the flexibility of using something else when 
   needs arise. This will invert dependencies and will make PlaceViewModel testable
 - Move the creation of adapter outside of MainActivity so it can be mocked and tested
   then use an interface to pass data to it.
 - Build an entity translator that converts from any type of repository object into a Place
 - Build more tests on Empty parameters, non existing places.
 - So far there is one simple use case for GPS, get last known location and it isnt 
   testable as it is defined dependency. We can create a Location module that not only gets 
   last known GPS location and can be injected but also tracks changes in a geo fence to 
   update UI or give the ability to search by city.
 - Introduce persistent storage in the sense of storing favorite restaurants so they appear
   at the top (if distance is within range) or just access them offline any time in another
   view.
 
   
