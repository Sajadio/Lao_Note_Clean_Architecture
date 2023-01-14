package com.sajjadio.laonote.data.remote.firestore.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.data.remote.firestore.TasksDocFireStore
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TasksDocFireStoreImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : TasksDocFireStore {
    override suspend fun setTask(task: Task): Void? {
        val setNote = HashMap<String, Any?>()
        setNote[TASK_ID] = task.task_id
        setNote[TASK_TITLE] = task.task_title
        setNote[TASK_DESCRIPTION] = task.task_description
        setNote[TASK_WEB_URL] = task.task_webUrl
        setNote[TASK_DATE_CREATED] = task.task_date_created
        val collection = fireStore.collection(TASKS)
        return collection.document(setNote[TASK_ID].toString()).set(setNote).await()
    }

    override suspend fun getTasks(): List<Task> =
        fireStore.collection(TASKS).get().await().toObjects(Task::class.java)

    override suspend fun getTasksByTitle(title: String): List<Task> {
        val tasks = mutableListOf<Task>()
        if (title.isEmpty()) {
            return getTasks()
        } else
            getTasks().forEach { task ->
                if ((task.task_title?.startsWith(title, true) == true) ||
                    task.task_title?.endsWith(title, true) == true
                ) {
                    tasks.add(task)
                }
            }
        return tasks
    }

    override suspend fun getTaskOrderBy(order: Boolean): List<Task> =
        fireStore.collection(TASKS).whereEqualTo(IS_DONE,order).get().await().toObjects(Task::class.java)


    override suspend fun updateTask(task: Task): Void? {
        val updateTask = HashMap<String, Any?>()
        updateTask[TASK_ID] = task.task_id
        updateTask[TASK_TITLE] = task.task_title
        updateTask[TASK_DESCRIPTION] = task.task_description
        updateTask[TASK_WEB_URL] = task.task_webUrl
        updateTask[TASK_DATE_CREATED] = task.task_date_created
        return fireStore.collection(TASKS).document(updateTask[TASK_ID].toString())
            .update(updateTask).await()
    }

    override suspend fun isTaskDone(task: Task): Void? {
        val updateTask = HashMap<String, Any?>()
        updateTask[TASK_ID] = task.task_id
        updateTask[IS_DONE] = task.is_done
        return fireStore.collection(TASKS).document(updateTask[TASK_ID].toString())
            .update(updateTask).await()
    }

    override suspend fun deleteTaskByID(taskId: String): Void? =
        fireStore.collection(TASKS).document(taskId).delete().await()
}