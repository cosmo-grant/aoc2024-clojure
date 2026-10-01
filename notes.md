# Notes

## TODO

Fix editor bracketing plugin
- Doesn't double ( when cursor before a word character
- Does double ' but shouldn't (at least not in clj)
- Ignore ) when cursor at a ) which leads to losing brackets

Lots of cast vec - list issues

## How to run with flowstorm

`lein with-profile +flowstorm repl`
`:dbg`
