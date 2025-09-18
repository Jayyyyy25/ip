# Remy's User Guide

Welcome to Remy! Remy is a command-line based task manager that helps you keep track of your daily tasks efficiently. 
With simple text commands, you can manage your tasks easily. Lightweight, reliable, and user-friendly, 
Remy designed to help you stay organized, your personal task assistant on the CLI.

![Product UI Screenshot](/docs/Ui.png)

## Before getting started

1. Ensure you have Java 17 installed on your system.
2. Download the latest version of Remy JAR file from the repository.
3. Open your terminal and navigate to the directory where you downloaded the JAR file.
4. Run the application using the command: 
    ```
    java -jar remy.jar
    ```
5. Start managing your tasks with Remy!

## Features

### 1. Adding a ToDo Task: `todo`

Add a simple task without any date or time attached.

Format: `todo <task_description>`

Example:
- `todo Read a book`
- `todo Buy groceries`

### 2. Adding a Deadline Task: `deadline`

Add a task that has a specific deadline.

Format: `deadline <task_description> /by <deadline>`

Example:
- `deadline CS2103T assignment /by 2025-09-2025 23:59`
- `deadline Submit internship application /by 2025-10-01`
> Tips: The date format is `YYYY-MM-DD` or `DD-MM-YYYY` and time is optional in `HH:MM` format.\
> `-` or `/` can both be used as separators.

### 3. Adding an Event Task: `event`

Add a task that occurs at a specific time or date range.

Format: `event <task_description> /from <start_time> /to <end_time>`

Example:
- `event CS2103T tP team meeting /from 2025-09-23 15:00 /to 2025-09-23 16:00`
- `event Club bonding camp /from 2025-10-05 /to 2025-10-07`

### 4. Listing Tasks: `list`

Display your tasks with their status (completed or not).

Format: `list [/on <date>]`
> Tips: /on date is optional. If not provided, all tasks will be listed.
> 
Example:
- `list`
- `list /on 2025-09-23`

Example output:

![List Command Screenshot](/docs/list.png)

### 5. Marking a Task as Done: `mark`

Mark a task as completed based on the indexing on listing.

Format: `mark <task_index>`

Example:
- `mark 2`
> Tips: You can find the task index by using the `list` command.
> 
> Only use the index from the most recent `list` command, but not indexes on list shown by `list /on <date>`.

### 6. Unmarking a Task: `unmark`

Unmark a task as not completed based on the indexing on listing.

Format: `unmark <task_index>`

Example:
- `unmark 2`
>Tips: You can find the task index by using the `list` command.
> 
> Only use the index from the most recent `list` command, but not indexes on list shown by `list /on <date>`.

### 7. Deleting a Task: `delete`

Delete a task from your list based on the indexing on listing.

Format: `delete <task_index>`

Example:
- `delete 3`
> Tips: You can find the task index by using the `list` command.
> 
> Only use the index from the most recent `list` command, but not indexes on list shown by `list /on <date>`.


### 8. Finding Tasks: `find`

Search for tasks that contain a specific keyword in their description.

Format: `find <keyword>`

Example:
- `find assignment`
- `find meeting`
> Tips: The search is case-insensitive and will return all tasks that contain the keyword.

### 9. Exiting the Application: `bye`

Exit the Remy application safely.

Format: `bye`

### 10. Storage

All your tasks are saved automatically in hard disk.
This ensures that your tasks are preserved even after you close the application./
There is no need to save manually.

### 11. Editing Tasks from the File

Remy's data are saved automatically as a text file under `[JAR file location]/data/remy.txt`.
Users are welcomed to access to the file directly to add, remove, or modify tasks.
> Tips: :bangbang: Please ensure to follow the existing format in the file to avoid any loading issues.

## Command Summary
| Command | Description |
|---------|-------------|
| `todo <task_description>` | Add a ToDo task |
| `deadline <task_description> /by <deadline>` | Add a Deadline task |
| `event <task_description> /from <start_time> /to <end_time>` | Add an Event task |
| `list [/on <date>]` | List all tasks or tasks on a specific date |
| `mark <task_index>` | Mark a task as done |
| `unmark <task_index>` | Unmark a task as not done |
| `delete <task_index>` | Delete a task |
| `find <keyword>` | Find tasks containing a keyword |
| `bye` | Exit the application |

Hope you find Remy helpful in managing your tasks! 
If you have any questions or need further assistance, feel free to reach out!
