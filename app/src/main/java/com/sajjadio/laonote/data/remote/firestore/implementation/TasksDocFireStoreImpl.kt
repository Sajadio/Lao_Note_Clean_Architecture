package com.sajjadio.laonote.data.remote.firestore.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.data.remote.firestore.TasksDocFireStore
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TasksDocFireStoreImpl @Inject constructor(
    fireStore: FirebaseFirestore
) : TasksDocFireStore {

    private val userCollection = fireStore.collection(USER)

    override suspend fun setTask(task: Task): Void? {
        val setTask = HashMap<String, Any?>()
        setTask[USER_ID] = task.user_id
        setTask[TASK_ID] = task.task_id
        setTask[TASK_TITLE] = task.task_title
        setTask[TASK_DESCRIPTION] = task.task_description
        setTask[TASK_WEB_URL] = task.task_webUrl
        setTask[TASK_DATE_CREATED] = task.task_date_created
        setTask[IS_DONE] = task.is_done
        return userCollection
            .document(setTask[USER_ID].toString())
            .collection(TASKS)
            .document(setTask[TASK_ID].toString())
            .set(setTask)
            .await()
    }

    override suspend fun getTasks(userID: String): List<Task> = userCollection
        .document(userID)
        .collection(TASKS)
        .get()
        .await()
        .toObjects(Task::class.java)

    override suspend fun searchAboutTask(task: Task): List<Task> {
        val tasks = mutableListOf<Task>()
        if (task.task_title?.isEmpty() == true) {
            return getTasks(task.user_id.toString())
        }
        getTasks(task.user_id.toString()).forEach {
            if ((it.task_title?.startsWith(task.task_title.toString(), true) == true) ||
                it.task_title?.endsWith(task.task_title.toString(), true) == true
            ) {
                tasks.add(it)
            }
        }
        return tasks
    }

    override suspend fun getTaskOrder(task: Task): List<Task> = userCollection
        .document(task.user_id.toString())
        .collection(TASKS)
        .whereEqualTo(IS_DONE, task.is_done)
        .get()
        .await()
        .toObjects(Task::class.java)


    override suspend fun updateTask(task: Task): Void? {
        val updateTask = HashMap<String, Any?>()
        updateTask[USER_ID] = task.user_id
        updateTask[TASK_ID] = task.task_id
        updateTask[TASK_TITLE] = task.task_title
        updateTask[TASK_DESCRIPTION] = task.task_description
        updateTask[TASK_WEB_URL] = task.task_webUrl
        updateTask[TASK_DATE_CREATED] = task.task_date_created

        return userCollection
            .document(updateTask[USER_ID].toString())
            .collection(TASKS)
            .document(updateTask[TASK_ID].toString())
            .update(updateTask).await()
    }

    override suspend fun isTaskDone(task: Task): Void? {
        val updateTask = HashMap<String, Any?>()
        updateTask[TASK_ID] = task.task_id
        updateTask[IS_DONE] = task.is_done
        return userCollection
            .document(task.user_id.toString())
            .collection(TASKS)
            .document(updateTask[TASK_ID].toString())
            .update(updateTask).await()
    }

    override suspend fun deleteTask(task: Task): Void? = userCollection
        .document(task.user_id.toString())
        .collection(TASKS)
        .document(task.task_id)
        .delete()
        .await()
}