Author: Anna Magdalena Bustowska


# StudyVerse
*Orbit your Goals*  ✨

## Features
- Dashboard
- Tasks & Exams
- Lectures & Timetable
- Notes
- NASA Picture of the Day

## Screenshots

<div>
  <img src="https://github.com/user-attachments/assets/ed48d9cc-09f0-4a32-bf43-41f7da9e700b" alt="dashboard" width="170">&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/3fe494df-7420-4440-80fb-db1414d9d106" alt="tasks" width="170">&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/e3444a3f-8f56-400e-8a21-4556f7e25be5" alt="exam" width="170">&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/136d7c80-4643-4583-9d31-4eb3fa2aeb29" alt="lectures" width="170">
</div>

<br>

<div>
  <img src="https://github.com/user-attachments/assets/534eb1c1-6705-4526-b64f-a6e8f8ec2c63" alt="dayViewLecture" width="170">&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/f8cc1e0f-b4c3-479f-80ce-62d6d903801b" alt="notescreen" width="170">&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/12a3299f-8da8-4bc3-a815-83efa6d36a1b" alt="note" width="170">&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/6987b3c0-0938-4b7a-bf76-50472e4c0be9" alt="space" width="170">
</div>


## Application Flowchart

<img width="2767" height="1445" alt="MyAppStudyVerseFlowchartV3" src="https://github.com/user-attachments/assets/daf2c1a2-9328-4f89-b2fb-cfad0a554ec0" />


## Demo

[Watch the StudyVerse Demo](https://drive.google.com/file/d/1XmJHB_pjRiaaQYraGsICTF6XR3GuL86t/view?usp=sharing)


## Technologies

- Kotlin
- Jetpack Compose
- Android Studio
- Room Database
- Firebase Authentication
- Retrofit 
- NASA APOD API 


## Error Handling & Validation

- The app validates required input fields before saving data.
- Invalid or incomplete input is handled directly in the corresponding detail screens.
- Network and API errors are handled with user-friendly error messages.


## Known Issues

- An Exam created from the Task Screen may open as a Task instead of an Exam.
- Some screens still have loading/transition behavior that could be smoother.
- Some UI elements and screens need further color and contrast refinement.
  


## Future Improvements

- Add a Date Picker for Tasks and Exams instead of manual date input.
- Add database migration support for future database schema changes and app updates.
- Improve the visual design of Lecture Cards and expand the Timetable to include weekends.
- Introduce a clickable Profile view with Settings.
- Add Dark/Light Theme selection and potentially additional visual themes.
- Add Password Reset functionality.
- Explore linking Notes, Tasks and Lectures through hashtags or related entities.
- Further improve loading states, animations and screen transitions.
- Refine and centralize the color palette and allow users to choose different accent themes and wallpapers.
