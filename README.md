# RichRail

The objective was to rebuild a pourly designed application called "PoorRail", by using  various design patterns and the SOLID design principles.

Some of the design patterns we used are:
- Facade
- Observer
- Builder
- Iterator
- Decorator 

Feedback and improvement:
- Use a factory to set the right database implementation.
- ComponentBuilder is not open/closed, a strategy or brdige pattern could be used to fix that.
- When having multiple (observer) subjects the subject needs to know if it should update itself.


The final grade of this assignment was a 9.1
