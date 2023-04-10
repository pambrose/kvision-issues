# KVision Issue with Single Field Forms

Single field forms are not working properly. If a form has more than one field, the
*onEvent{}* for *keydown* is called as expected. However, if a form has only one field,
the *onEvent{}* for *keydown* is not called and the page is instead reloaded.

## Steps to reproduce:

1) Run this app
2) Click on Field 1 of 2 and hit ENTER
3) The Alert is displayed
4) Click on Field 2 of 2 and hit ENTER
5) The Alert is displayed
6) Click on Field 1 of 1 and hit ENTER
7) Notice that the page is reloaded and the Alert is not displayed