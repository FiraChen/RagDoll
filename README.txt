Yufei Chen
20707983 y2285che
openjdk version "12.0.2" 2019-07-16
macOS 10.14.4 (MacBook Air 2015)

There is an "app-debug.apk" file at the top level of a5 directory as required in the assignment document.
MyDoll app can be run in Pixel C using API 29. It will start as landscape version, then, you can choose to rotate to switch to vertical version.

There are two buttons on the left top corner:
  1. Reset: return the doll to its starting size, and position.
  2. About: Display a popup with application name, my name and student number.

The doll can be manipulated by:
  1. Touching and dragging torso will translate the doll.
  2. Touching and moving other parts will rotate the touched part.
  3. Pinch or spread legs using two fingers will scale the leg in the vertical direction around the focal point
        (horizontal scale won't change otherwise the doll will look strange), while feet will remain unchanged.
