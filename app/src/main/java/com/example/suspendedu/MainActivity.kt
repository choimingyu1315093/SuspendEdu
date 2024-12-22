package com.example.suspendedu

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.suspendedu.ui.theme.SuspendEduTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuspendEduTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        ForLoopWithoutYieldExample(applicationContext)
                    }
                }
            }
        }
    }
}

fun blockingTask(): String {
    Thread.sleep(10000)
    return "일반 함수"
}

suspend fun suspendTask(): String {
    delay(10000)
    return "suspend 함수"
}

@Composable
fun ForLoopWithoutYieldExample(context: Context) {
    val scope = rememberCoroutineScope()
    var result by remember { mutableStateOf("데이터 없음") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(result)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            result = blockingTask()
        }) {
            Text("일반 함수 테스트")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                result = suspendTask()
            }
        }) {
            Text("suspend 함수 테스트")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                Toast.makeText(context, "메시지 호출", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("메시지 호출")
        }
    }
}

