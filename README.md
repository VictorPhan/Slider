# Proposed Implemented Agent A.I for Slider

*Tin Bao, Victor Phan - The University of Melbourne*

## Introduction

​	Through testing of the game Slider and its rules, it was concluded that the game had distinct similarities to chess. Hence, inspiration and programming techniques were drawn from chess engines such as *BitBoard* and *StockFish*. By utilizing an unorthodox approach to the design problem, we hope to minimize time complexity and maximize efficiency and difficulty because the stored types will be very different to the standard.

​	We will be utilizing an iterative approach to the design problem which mirrors what is required in the real workplace. By utilizing this approach, gradual improvements to the proposed design can be made and down-scoping can be done if the task is deemed too difficult. This is intended to further our skills and deepen the subconscious thinking of group project work. 

## Algorithm of Choice

​	The chosen algorithm will be *alpha-beta pruning*, which will utilize a lower and upper bound for the search. This will create a near optimal and complete solution to the search problem.

**Pseudo-code**

```
function ALPHA-BETA-SEARCH(state) returns an action
	v ← MAX-VALUE(state, -∞, +∞)
	return the action in ACTIONS(state) with value v

function MAX-VALUE(state, α, β) returns a utility value
	if TERMINAL-TEST(state) then return UTILITY(state)
	v ← -∞
	for each a in ACTIONS(state) do
		v ← MAX(v, MIN-VALUE(RESULT(s, a), α, β))
		if v ≥ β then return v
		α ← MAX(α, v)
	return v
	
function MIN-VALUE(state, α, β) returns a utility value
	if TERMINAL-TEST(state) then return UTILITY(state)
	v ← +∞
	for each a in ACTIONS(state) do
		v ← MIN(v, MAX-VALUE(RESULT(s, a), α, β))
		if v ≤ α then return v
		β ← MIN(β, v)
	return v

```

