# Ludo
Distributed implementation of the board game Ludo using Java RMI.

## Usage

One player creates the game room:
```
./ludo nickname port
```

The other players connect to the game room:
```
./ludo nickname port remote_port [remote_host]
```

In case the players are connecting to a game room running on the same host, the `remote_host` argument is optional.
