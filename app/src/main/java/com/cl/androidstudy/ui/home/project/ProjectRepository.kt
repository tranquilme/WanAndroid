package com.cl.androidstudy.ui.home.project

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.ArticleResponse
import com.cl.androidstudy.logic.model.CategoryResponse
import com.cl.androidstudy.logic.model.ProjectResponse
import com.cl.androidstudy.logic.network.ApiNetwork
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException

object ProjectRepository {
    fun getProjectData(page: Int, cid: Int) = liveData {
        val res = try {
            coroutineScope {
                val categoryResponse = async {
                    ApiNetwork.getCategory()
                }
                val projectResponse = async {
                    ApiNetwork.getProject(page, cid)
                }
                val category = categoryResponse.await()
                val project = projectResponse.await() // 两个协程并行,在async代码块内代码执行完之前阻塞当前协程
                if (category.errorCode == 0 && project.errorCode == 0) {
                    val projectData = ProjectResponse(category, project)
                    Result.success(projectData)
                }else {
                    Result.failure(RuntimeException("category errorCode is ${category.errorCode},project errorCode is ${project.errorCode}"))
                }
            }
        } catch (e: Exception) {
            Result.failure<ProjectResponse>(e)
        }
        emit(res)
    }
//    fun getCategory() = liveData {
//        val res = try {
//            val categoryResponse = ApiNetwork.getCategory()
//            if (categoryResponse.errorCode == 0) {
//                Result.success(categoryResponse)
//            } else {
//                Result.failure(RuntimeException("errorCode is ${categoryResponse.errorCode}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<CategoryResponse>(e)
//        }
//        emit(res)
//    }

//    fun getPorject(page: Int, cid: Int) = liveData {
//        val res = try {
//            val projectResponse = ApiNetwork.getProject(page, cid)
//            if (projectResponse.errorCode == 0) {
//                Result.success(projectResponse)
//            } else {
//                Result.failure(RuntimeException("errorCode is ${projectResponse.errorCode}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<ArticleResponse>(e)
//        }
//        emit(res)
//    }
}