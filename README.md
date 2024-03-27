<h1>Note App</h1>

<p >  
Note App demonstrates modern Android development with (Room, DataBase),MVVM, Repository Design and Navigation Component  . 
</p>

# Screenshots:
<img width="388" alt="Screenshot 2024-03-27 at 7 09 46 PM" src="https://github.com/Nour5Eldin/News/assets/145837378/fbb6ed78-73f3-4497-bad1-552bff3a9f19">
<img width="391" alt="Screenshot 2024-03-27 at 7 10 28 PM" src="https://github.com/Nour5Eldin/News/assets/145837378/36ea1317-459f-42db-9ecb-19ca14c9b8a9">
<img width="395" alt="Screenshot 2024-03-27 at 7 12 46 PM" src="https://github.com/Nour5Eldin/News/assets/145837378/bf26f204-d24e-4727-8d77-e06a7c9a82d9">
<img width="393" alt="Screenshot 2024-03-27 at 7 13 12 PM" src="https://github.com/Nour5Eldin/News/assets/145837378/a3d41489-d16e-4c63-a1c1-eb24b9226ffa">
<img width="401" alt="Screenshot 2024-03-27 at 7 13 39 PM" src="https://github.com/Nour5Eldin/News/assets/145837378/e6d88648-6849-41ac-ae41-2e6500574fd4">
<img width="392" alt="Screenshot 2024-03-27 at 7 35 03 PM" src="https://github.com/Nour5Eldin/News/assets/145837378/c2b44790-775a-491e-b1c6-6c5dd712d2b5">

## Tech stack & Open-source libraries
- Minimum SDK level 26
- [Kotlin](https://kotlinlang.org/)
  - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewBinding: is one of the best features which provides the views to bind with the activity which is ongoing. Replacing the findViewById().
  - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - Fragment: represents a reusable portion of your app's UI. A fragment defines and manages its own layout, has its own lifecycle, and can handle its own input events
  - Navigation Component : Navigation refers to the interactions that let users navigate across, into, and back out from the different pieces of content within your app..
  - Safe Args: Safe Args that generates simple object and builder classes for type-safe navigation and access to any associated arguments
  - Repository Design Pattern : The Repository Pattern is a design pattern used in software development that provides a way to manage data access logic in a centralized location. It separates the logic that retrieves 
   the data and maps it to the entity model from the business logic that operates on the model. The primary objective of the Repository Pattern is to simplify the data access code and achieve a cleaner architecture 
   for the application.  - Model View ViewModel : Developers always prefer clean and structured code for projects. Organizing the codes according to a design pattern helps in the maintenance of the software. By having 
    knowledge of all crucial logic parts of the android application,
    it is easier to add and remove app features. Further, design patterns also assure that all the codes get covered in Unit Testing without the interference of other classes.
  - Model — View — ViewModel (MVVM) is the industry-recognized software architecture pattern that overcomes all drawbacks of MVP and MVC design patterns. MVVM suggests separating the data presentation logic(Views or 
    UI) from the core business logic part of the application. 
    The separate code layers of MVVM are Model: This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.
    View: The purpose of this layer is to inform the ViewModel about the user’s action. This layer observes the ViewModel and does not contain any kind of application logic.
    ViewModel: It exposes those data streams which are relevant to the View. Moreover, it serves as a link between the Model and the View.
    ![mad-arch-overview](https://github.com/Nour5Eldin/News/assets/145837378/bb74ba4b-6d32-4241-80a3-c0da82cdb4ed)
    
