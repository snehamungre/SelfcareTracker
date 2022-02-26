# Self care Tracker

### About the project:
For my personal project I thought it would be a good idea to create a health tracker. It has a grater emphasis on self care, rather than fitness.

The user will be able to track their:
- Sleep
- Water intake
- Mood
- Meditation/breathing
- Journaling 

It also allows users to set goals and users can add and check their progress throughout the day.  
Users can analyse how their mood, sleep and other factors have an impact on them with an overview page which shows the 
data for the past days. 

### User Stories
- As a user, I want to be able to add a 'Trackers' to the 'Goals'.
- As a user, I want to be able to delete a 'Trackers' to the 'Goals'.
- As a user, I want to be able to add to my daily progress towards each of the 'Goals'.
- As a user, I want to be able to view my progress though the day for all 'Goals'.

- As a user, I want to be able to save my progress for the day for all 'Days'.
- As a user, I want to be able to automatically load my progress for all 'Days'.
- As a user, I want to be able to change the target set for the 'Trackers' in 'Goals'.
- As a user, I want to a reminder to save my progress before quitting. 

- As a user, I want to track my 'Goals' over multiple 'Days'.
- As a user, I want to be able to either edit the current day or add a new day without having to reset my 'Goals' and 'Trackers'.

_All icons are from: https://icons8.com_
_Audio: https://www.youtube.com/watch?v=sMHrq9NavWI_


#### Phase 4: Task 2
- **Robust Code:** Due to the console based implementation of the program, it was possible for the user to type the wrong word due to which the KeyError was added to _Goals_ class.
This is also important as this is also the key to retrieve the trackers which have been selected from the map. Due to this setting the key which is inputted is important and so the `addGoal()`method throws the KeyError.
- **Type Hierarchy:** The abstract class _Trackers_ was created to create different types of trackers with their own functionality without duplication of code. Due to this, most trackers inherit the methods for updating and adding a target to each tracker.
However, some methods were overridden in MoodTracker and SleepTracker.
- **Map interface:** It is used in _Goals_ to easily retrieve a tracker, regardless of when it was added.
- **Bi-Directional Association:** This is used in _DisplayPanel_ and _MenuPanel_. This because the menu panel makes the changes which the display panel is responsible for displaying.

#### Phase 4: Task 3
One aspect of the design of the code that I would like to improve is the hierarchy for the trackers. This would have been better implemented if trackers was an interface with two abstract classes which implemented it instead. 
This would split the current trackers which I have into two parts, namely ones which have targets and progress and others which simply display the information which the user has entered. 
The _MoodTracker_ is one such example which really doesn't need a target and progress but rather is just a reflection of the mood that the user had on the day. 

This also in turn also affects the GUI display. As the MoodTracker should be one of three values (1,2,3), rather than a text field, Radio Buttons would have been a better way to implement the design. 

Another aspect of the GUI which can be improved is adding progress bars to show the progress of the trackers rather than just displaying the target the progress text. 

Lastly, there is a lot of coupling between the two '_Days_' and '_Goals_'. It would have been helpful to implement the Iterator pattern this the design rather than having goals give out the number of _Goals_ present in _Days_ each time. 
This method of implementing the iterator requires knowledge of the internal structure of goals and doesn't adhere to the design principles of object oriented design.  
