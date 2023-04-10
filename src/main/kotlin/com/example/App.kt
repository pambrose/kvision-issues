package com.example

import io.kvision.Application
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapModule
import io.kvision.CoreModule
import io.kvision.core.onEvent
import io.kvision.form.formPanel
import io.kvision.form.text.Text
import io.kvision.html.br
import io.kvision.html.h3
import io.kvision.modal.Alert
import io.kvision.module
import io.kvision.panel.root
import io.kvision.panel.simplePanel
import io.kvision.startApplication
import io.kvision.utils.ENTER_KEY
import io.kvision.utils.px
import kotlinx.serialization.Serializable

class App : Application() {

  @Serializable
  data class FormData(val field1: String, val field2: String = "")

  override fun start() {
    root("kvapp") {

      simplePanel {
        paddingLeft = 30.px

        h3 { +"A form with 2 fields properly displays the Alert when ENTER is pressed" }

        formPanel {
          width = 300.px
          add(
            key = FormData::field1,
            control = Text(label = "Field 1 of 2:"),
          )
          add(
            key = FormData::field2,
            control = Text(label = "Field 2 of 2:"),
          )

          onEvent {
            keydown = {
              if (it.keyCode == ENTER_KEY)
                Alert.show(text = "Enter pressed")
            }
          }
        }
      }

      br {}
      br {}

      simplePanel {
        h3 { +"A form with 1 field reloads the page and does not display the Alert when ENTER is pressed" }
        paddingLeft = 30.px
        formPanel {
          width = 300.px
          add(
            key = FormData::field1,
            control = Text(label = "Field 1 of 1:"),
          )

          onEvent {
            keydown = {
              if (it.keyCode == ENTER_KEY)
                Alert.show(text = "Enter pressed")
            }
          }
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
    CoreModule
  )
}
