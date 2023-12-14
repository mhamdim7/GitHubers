# GitHuers

GitHuers is an Android application that utilizes the GitHub REST API to search for users based on a provided keyword. It presents a list of matching profiles based on the search term, and tapping on a profile reveals more details.
## Table of Contents

- [Tech Stack](#description)
- [UX](#ux)
- [Info](#info)

## Tech Stack

This Android app utilizes Dagger Hilt for dependency injection, Jetpack Compose for UI, Coroutines for asynchronous tasks, Navigation Component for app navigation, Retrofit for API requests, and follows MVVM architecture with Clean Architecture principles.

## UX

The app is designed with a very basic interface and supports Dark and Light modes and adapting to Android themes' color palettes, it provides a straightforward experience when searching for and viewing GitHub users. It incorporates error handling for various situations and loading states to indicate ongoing operations, 

## Info

The app manages errors like 403 due to query limits (60 queries/IP/hour) on GitHub's API. It continues to return errors until accessed from a different network or after one hour.
