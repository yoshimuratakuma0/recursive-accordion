# RecursiveAccordion
[Screen_recording_20251010_010405.webm](https://github.com/user-attachments/assets/39a23a45-9e0c-4bd9-b85c-c48aa1045f11)



Android 向けの再帰的なアコーディオン UI コンポーネントライブラリです。Jetpack Compose
を使用して、ツリー構造のデータを階層的に表示できます。

## 特徴

- 🎨 **柔軟なカスタマイズ** - コンテンツ、アクション、スタイルを自由にカスタマイズ可能
- 🎬 **スムーズなアニメーション** - 展開・折りたたみ時の滑らかなアニメーション
- 📱 **Compose Multiplatform 対応** - Android だけでなく iOS にも対応
- 🔄 **無限の階層** - 任意の深さのツリー構造に対応
- 🎯 **シンプルな API** - 直感的で使いやすい API 設計

## インストール

`build.gradle.kts` に以下を追加してください：

```kotlin
dependencies {
    implementation("com.legstart:recursive-accordion-android:1.0.2")
}
```

## 基本的な使い方

### 1. データモデルの作成

```kotlin
// データを RecursiveItem でラップ
val item = RecursiveItem(
    id = "1",
    value = "親アイテム",
    children = listOf(
        RecursiveItem(id = "1-1", value = "子アイテム 1"),
        RecursiveItem(id = "1-2", value = "子アイテム 2"),
        RecursiveItem(
            id = "1-3",
            value = "子アイテム 3",
            children = listOf(
                RecursiveItem(id = "1-3-1", value = "孫アイテム 1"),
                RecursiveItem(id = "1-3-2", value = "孫アイテム 2"),
            )
        )
    )
)
```

### 2. RecursiveAccordion の使用

```kotlin
@Composable
fun MyScreen() {
    // 展開状態を管理
    val expandedIds = remember { mutableStateSetOf<String>() }

    RecursiveAccordion(
        item = item,
        expandedIds = expandedIds,
        onToggle = { toggledItem ->
            if (toggledItem.id in expandedIds) {
                expandedIds.remove(toggledItem.id)
            } else {
                expandedIds.add(toggledItem.id)
            }
        },
        action = { current, isExpanded, onToggle ->
            // 展開/折りたたみボタン
            IconButton(onClick = onToggle) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        rotationZ = if (isExpanded) 180f else 0f
                    }
                )
            }
        }
    ) { current ->
        // アイテムのコンテンツ
        Text(text = current.value)
    }
}
```

## カスタマイズ

### アニメーションのカスタマイズ

```kotlin
RecursiveAccordion(
    item = item,
    expandedIds = expandedIds,
    onToggle = { /* ... */ },
    animationDurationMillis = 500, // アニメーション時間（ミリ秒）
    // ...
) { /* ... */ }
```

### レベルガイドのカスタマイズ

```kotlin
RecursiveAccordion(
    item = item,
    expandedIds = expandedIds,
    onToggle = { /* ... */ },
    showLevelGuide = true, // レベルガイドの表示
    levelGuideWidth = 2.dp, // ガイドの幅
    levelGuideColor = Color.Gray, // ガイドの色
    levelLeadingPadding = 16.dp, // 各レベルのインデント幅
    // ...
) { /* ... */ }
```

### LazyColumn での使用

```kotlin
@Composable
fun ItemList(items: List<RecursiveItem<String>>) {
    val expandedIds = remember { mutableStateSetOf<String>() }

    LazyColumn {
        items(items) { item ->
            RecursiveAccordion(
                item = item,
                expandedIds = expandedIds,
                onToggle = { toggledItem ->
                    if (toggledItem.id in expandedIds) {
                        expandedIds.remove(toggledItem.id)
                    } else {
                        expandedIds.add(toggledItem.id)
                    }
                },
                action = { current, isExpanded, onToggle ->
                    IconButton(onClick = onToggle) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.graphicsLayer {
                                rotationZ = if (isExpanded) 180f else 0f
                            }
                        )
                    }
                }
            ) { current ->
                Text(
                    text = current.value,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
```

## API リファレンス

### RecursiveItem

```kotlin
data class RecursiveItem<T>(
    val id: String,              // アイテムの一意な ID
    val value: T,                // アイテムの値
    val children: List<RecursiveItem<T>> = emptyList() // 子アイテム
)
```

### RecursiveAccordion パラメータ

| パラメータ                     | 型                                                             | デフォルト値              | 説明                    |
|---------------------------|---------------------------------------------------------------|---------------------|-----------------------|
| `item`                    | `RecursiveItem<T>`                                            | -                   | 表示するアイテム（必須）          |
| `expandedIds`             | `Set<String>`                                                 | `emptySet()`        | 展開されているアイテムの ID セット   |
| `onToggle`                | `(RecursiveItem<T>) -> Unit`                                  | `{}`                | アイテムの展開/折りたたみ時のコールバック |
| `action`                  | `@Composable (RecursiveItem<T>, Boolean, () -> Unit) -> Unit` | -                   | 展開/折りたたみボタンのコンポーザブル   |
| `content`                 | `@Composable (RecursiveItem<T>) -> Unit`                      | -                   | アイテムコンテンツのコンポーザブル（必須） |
| `modifier`                | `Modifier`                                                    | `Modifier`          | モディファイア               |
| `levelLeadingPadding`     | `Dp`                                                          | `4.dp`              | 各レベルのインデント幅           |
| `animationDurationMillis` | `Int`                                                         | `250`               | アニメーション時間（ミリ秒）        |
| `showLevelGuide`          | `Boolean`                                                     | `true`              | レベルガイドを表示するか          |
| `levelGuideWidth`         | `Dp`                                                          | `1.dp`              | レベルガイドの幅              |
| `levelGuideColor`         | `Color`                                                       | `Color(0xFFE0E0E0)` | レベルガイドの色              |

## サンプルコード

完全なサンプルコードは [composeApp/src/commonMain/kotlin/com/legstart/recursiveaccordion/sample](composeApp/src/commonMain/kotlin/com/legstart/recursiveaccordion/sample)
を参照してください。

## ライセンス

```
Copyright 2025 Takuma Yoshimura

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

