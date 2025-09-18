# Tom User Guide

![Product Screenshot](/docs/Ui.png)

Tom is a task management application that helps you manage your **ToDos**, **Deadlines**, and **Events** efficiently.  
This guide explains how to add different types of tasks and outlines key features.

- Features  
  - [Adding a deadline : `deadline`](#adding-deadlines)
  - [Adding a todo : `todo`](#adding-ToDos)
  - [Adding a event : `event`](#adding-Events)
  - [View task list : `view`](#view-task-list)
  - [Editing a person : `edit`](#editing-a-person)
  - [Mark task : `mark`](#locating-persons-by-name)
  - [Deleting a person : `delete`](#deleting-a-person)
  - [Clearing all entries : `clear`](#clearing-all-entries)
  - [Exiting the program : `exit`](#exiting-the-program)
  - [Saving the data](#saving-the-data)
  - [Editing the data file](#editing-the-data-file)
  - [Archiving data files `[coming in v2.0]`](#archiving-data-files)


## Adding deadlines

Deadlines are tasks that must be done before a specific date/time.  
Use the `/by` keyword to specify the deadline.

**Example:**
```
deadline return book /by 2026-03-12 00:00
```

**Expected Outcome:**
```
Got it. I've added this task:
[D][ ] return book (by: 12 Mar 2026 00:00)
Now you have X tasks in the list.
```

## Adding ToDos

ToDos are tasks without any date/time attached to them.  
Simply use the `todo` keyword followed by the task description.

**Example:**
```
todo borrow book
```

**Expected Outcome:**
```
Got it. I've added this task:
[T][ ] borrow book
Now you have X tasks in the list.
```

## Adding Events

Events are tasks that start at a specific date/time and end at a specific date/time.  
Use the `/from` and `/to` keywords to specify the start and end times.

**Example:**
```
event project meeting /from 2026-03-12 14:00 /to 2026-03-12 16:00
```

**Expected Outcome:**
```
Got it. I've added this task:
[E][ ] project meeting (from: 12 Mar 2026 14:00 to: 12 Mar 2026 16:00)
Now you have X tasks in the list.
```

## View task list

View the list of task added into the application

**Example**
```
list
```

**Expected Outcome:**
```
Here are the tasks in your list:
1.[D][ ] return book (by: 12 Mar 2026 12:00am)
2.[T][ ] borrow book
3.[E][ ] project meeting (from: 12 Mar 2026 14:00 to: 12 Mar 2026 16:00)
```

## Mark task

Mark task as completed or unmark them for better tracking the task list

**Example**
```
mark 1
```

**Expected Outcome:**
```
Nice! I've marked this task as done: [D][X] return book (by: 12 Mar 2026 12:00 am)
```

**Example**
```
unmark 1
```

**Expected Outcome:**
```
I've unmarked this task as done: [D][ ] return book (by: 12 Mar 2026 12:00 am)
```

## Delete task

Delete task from the task list

**Example**
```
delete 1
```

**Expected Outcome:**
```
Noted. I've removed this task:
[D][ ] return book (by: 12 Mar 2026 12:00 am)
Now you have 4 tasks in the list.
```

## Find similar task description

Find similar tasks in the task list

**Example**
```
find book
```

**Expected Outcome:**
```
Task matching "book":
[D][ ] return book (by: 12 Mar 2026 12:00 am)
```

## Find task occurring on a particular datetime

Retrieves all the tasks that fall on a particular datetime

**Example**
```
occur 2026-03-12 00:00
```

**Expected Outcome:**
```
On 2026-03-12 00:00 you have these activities:
[D][ ] return book (by: 12 Mar 2026 12:00 am)
```

## Feature Add

The add feature allows users to efficiently manage their tasks with a simple command-based interface.  
You can add, delete, and view tasks in a clear list format.


## Feature Mark

The mark feature allows users to mark their tasks as completed to more efficiently manage their incompleted task.

## Feature Find Task

The mark feature allows users to find certain task in the task list to better manage their tasks.

## Feature Mark

The mark feature allows users to mark their tasks as completed to more efficiently manage their incompleted task.
