package com.upsaclay.message.data.repository

import com.upsaclay.common.data.local.UserLocalDataSource
import com.upsaclay.common.domain.model.User
import com.upsaclay.common.domain.repository.UserRepository
import com.upsaclay.common.utils.e
import com.upsaclay.common.utils.formatHttpError
import com.upsaclay.common.utils.i
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.io.IOException

internal class UserRepositoryImpl{
}
