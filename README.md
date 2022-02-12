[![Open in Visual Studio Code](https://open.vscode.dev/badges/open-in-vscode.svg)](https://open.vscode.dev/vincentlev/hh-mindtracker-fullstack)
[![License](https://img.shields.io/badge/license-MIT-orange.svg?style=flat-square)](http://opensource.org/licenses/MIT)
![](https://img.shields.io/github/issues-raw/VincentLeV/hh-mindtracker-fullstack?style=flat-square)
<br/>

# Mind Tracker

## Table of Contents
[Introduction](#introduction)
<br/>
[Features](#features)
<br/>
[Tech Stack](#tech-stack)
<br/>
[Run The Project Locally?](#run-the-project-locally)
<br/>
[UI Examples](#ui-examples)
<br/>
[Demo](#demo)

## Introduction
This is a simple CRUD app for users to track their mood with authentication. The idea comes from my interest in healthcare, especially mental health. This app could be useful for people who have bipolar, BPD or mood disorders, etc. and are interested in tracking their daily mood.

The project is created for Server Programming course at Haaga-Helia University of Applied Sciences. 

## Features

1. Create/Read/Update/Delete users
    - Users can only be edited and deleted by the user that has "ADMIN" role
    - User' password can never be changed
    - Only the user that has "ADMIN" role can see all entries created by other users

2. Create/Read/Update/Delete entries
    - A user with role "USER" can only see its own entries
    - Entries are sorted by date
    - Entry's headline, date and time can't be empty

## Tech Stack

1. Java
2. Spring Boot
3. Thymeleaf
4. MongoDB
5. Bootstrap 5

## Run The Project Locally

1. Clone the project
2. Open the project in IDEA (Eclipse, IntelliJ, etc.)
3. Run the project

## UI Examples
<p align="center">
    <img src="https://user-images.githubusercontent.com/49280437/153726900-419724e4-563a-40c1-8c1f-81bd8a3c72af.jpg" alt="1" width="500px" />
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/49280437/153726902-9ce1cec4-8a25-4691-9e9d-3b23b1b68825.jpg" alt="2" width="500px" />
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/49280437/153726903-1cb93c58-8546-4c51-af65-80f8df535e3a.jpg" alt="3" width="500px" />
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/49280437/153726905-877d014d-92cd-4ec2-982e-e1b1552fd34b.jpg" alt="4" width="500px" />
</p>

## Demo
<a href="https://hh-mindtracker-fullstack.herokuapp.com" target="_blank">
    <p align="center">https://hh-mindtracker-fullstack.herokuapp.com</p>
</a>

<p align="center">
    <img src="https://user-images.githubusercontent.com/49280437/153727431-1111e4a1-8b60-4370-b1bb-a5bf23e315b1.gif" alt="gif" />
</p>
