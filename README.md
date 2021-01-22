# Hertel Mehlhorn Convex Decomposition Demonstrator

In this program, we demonstrate how the Hertel-Mehlhorn Convex Decomposition algorithm can be used to construct a 4-approximation for the optimal convex decomposition of a given simple polygon. This program has 3 stages: DRAWING, TRIANGULATING, and FINAL.

In the DRAWING stage, users input a simple polygon. Left-Click to add a point at the cursor and Right-Click to remove the closest point to the cursor.

In the TRIANGULATING stage, the program performs an ear-clipping triangulation of the shape and displays it to the user.

In the FINAL stage, the program removes diagonals in order so long as their removal does not cause a concave shape to form as a result. The result will be a 4-approximation for the optimal convex decomposition.