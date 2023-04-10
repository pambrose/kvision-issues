# KVision Issue with Single Field Forms

Single field forms are not working properly. The *onEvent{}* for *keydown* is not being called and the
page is reloaded instead.

## Steps to reproduce:

1) Run this app
2) Click on Field 1 of 2
3) The Alert is displayed
4) Click on Field 2 of 2
5) The Alert is displayed
6) Click on Field 1 of 1
7) Notice that the page is reloaded and the Alert is not displayed