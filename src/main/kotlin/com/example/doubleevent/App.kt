package com.example.doubleevent

import io.kvision.Application
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapModule
import io.kvision.CoreModule
import io.kvision.TabulatorCssBootstrapModule
import io.kvision.TabulatorModule
import io.kvision.core.StringPair
import io.kvision.core.onEvent
import io.kvision.form.InputSize
import io.kvision.form.select.Select
import io.kvision.form.text.TextInput
import io.kvision.form.time.DateTimeInput
import io.kvision.html.br
import io.kvision.html.button
import io.kvision.html.h2
import io.kvision.html.h3
import io.kvision.html.span
import io.kvision.module
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.startApplication
import io.kvision.state.bind
import io.kvision.state.observableListOf
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TableType
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import io.kvision.types.toDateF
import io.kvision.utils.px
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class App : Application() {

  private val selectItems = observableListOf<StringPair>()

  private val selectInput =
    Select(selectSize = 1) {
      width = 300.px
      options = List(3) { "$it" to "Choice $it" }
      // This assignment works properly
      selectedIndex = 2
    }.bind(selectItems) { items ->
      if (items.isNotEmpty()) {
        println("Assigning ${items.size}")
        options = items
        // This assignment has no effect on the selected item
        selectedIndex = -1 //items.size - 1
      }
    }

  override fun start() {
    root("kvapp") {
      h2 { +"First issue: selectedIndex not working for dynamically loaded data" }
      add(selectInput)
      button(text = "Replace Items in Select")
        .onClick {
          selectItems.clear()
          selectItems.addAll(List(3) { "${it + 3}" to "Button Choice ${it + 3}" })
        }

      br {}
      br {}
      span("<hr>", rich = true)
      br {}

      val output = observableListOf<String>()

      @Serializable
      class DateData(val dateValue: String, val textValue: String)

      val dates =
        observableListOf(
          DateData("02/01/2023", "Text 1"),
          DateData("02/02/2023", "Text 2"),
          DateData("02/03/2023", "Text 3"),
        )

      h2 { +"Second issue: double change events from date columns" }
      tabulator(
        dates,
        types = setOf(TableType.BORDERED, TableType.HOVER, TableType.STRIPED),
        serializer = serializer(),
        options = TabulatorOptions(
          layout = Layout.FITCOLUMNS,
          pagination = false,
          columns = listOf(
            ColumnDefinition(
              title = "Date Value",
              field = "dateValue",
              width = "40%",
              editable = { true },
              editorComponentFunction = { _, _, success, _, data ->
                DateTimeInput(value = data.dateValue.toDateF("MM/DD/YYYY"), format = "MM/DD/YYYY")
                  .apply {
                    //size = InputSize.SMALL
                    onEvent {
                      change = {
                        println("Date event")
                        output += "Date event"
                      }
                    }
                  }
              },
            ),
            ColumnDefinition(
              title = "Text Value",
              field = "textValue",
              width = "60%",
              headerSort = true,
              editable = { true },
              editorComponentFunction = { _, _, success, _, data ->
                TextInput(value = data.textValue).apply {
                  size = InputSize.SMALL
                  onEvent {
                    change = {
                      println("Text event")
                      output += "Text event"
                    }
                  }
                }
              },
            ),
          ),
        ),
      )
      br {}

      h3 { +"Output:" }
      vPanel {}.bind(output) {
        removeAll()
        output.forEachIndexed { i, s ->
          +"$i - $s"
          br {}
        }
      }

    }
  }
}

fun main() {
  startApplication(
    ::App,
    module.hot,
    BootstrapModule,
    BootstrapCssModule,
    TabulatorModule,
    TabulatorCssBootstrapModule,
    CoreModule
  )
}
