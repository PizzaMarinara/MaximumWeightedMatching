
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<div id="top"></div>
<h1 align="center">Maximum Weighted Matching</h1>

<p align="center">
A Kotlin implementation of the Swiss Pairing system using an algorithm that finds the maximum weighted matching in a graph.
</p>

<br>

## Made with

* [Kotlin](https://kotlinlang.org/)

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Including in your project

Add Jitpack (where the library is hosted) in your root `build.gradle` file:

```groovy
allprojects {
  repositories {
    maven { url "https://jitpack.io" }
  }
}
```

Then in your `build.gradle` module file where you want to use this library:

```groovy
dependencies {
  implementation "com.github.PizzaMarinara:MaximumWeightedMatching:{latest_version}"
}

```

## How to use

```kotlin
val edges = listOf(GraphEdge(1, 2, 10), GraphEdge(2, 3, 11))
// The graph defined by this list of GraphEdges has 4 nodes.
// node 0 has no edges connected to it.
// node 1 and node 2 are connected by an edge with a weight of 10.
// node 2 and node 3 are connected by an edge with a weight of 11.

// The rather obvious solution here is just the pair of node 2 and 3.
// Node 0 has no possible pairs, and node 1's only possible pair is already paired to node 3 with a higher weight.

// Calling maxWeightMatching returns a list of Pairs for each matching pair.
val matchingPairs: List<Pair<Long, Long>> = MaximumWeightedMatching.maxWeightMatching(edges)
// This returns [(0, -1), (1, -1), (2, 3)]

// Calling maxWeightMatchingList returns just a list of Long values, where each value is the paired value of its index (or -1 if it is not paired)
val matchingList: MutableList<Long> = MaximumWeightedMatching.maxWeightMatchingList(edges)
// This returns (-1, -1, 3, 2)

```

## Contact

Enrico Fantini<br>
[Twitter](https://twitter.com/Pizza_Marinara) - [Website](https://efantini.dev/)

Project Link: [https://github.com/PizzaMarinara/MaximumWeightedMatching](https://github.com/PizzaMarinara/MaximumWeightedMatching)

## Acknowledgments

The [python implementation of the swiss algorithm](https://github.com/bakert/swiss) done by [@bakert](https://github.com/bakert) is widely used to validate this code. The whole program is basically a rewrite of the python script in Kotlin.

<p align="right">(<a href="#top">back to top</a>)</p>


[contributors-shield]: https://img.shields.io/github/contributors/PizzaMarinara/MaximumWeightedMatching.svg?style=for-the-badge
[contributors-url]: https://github.com/PizzaMarinara/MaximumWeightedMatching/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/PizzaMarinara/MaximumWeightedMatching.svg?style=for-the-badge
[forks-url]: https://github.com/PizzaMarinara/MaximumWeightedMatching/network/members
[stars-shield]: https://img.shields.io/github/stars/PizzaMarinara/MaximumWeightedMatching.svg?style=for-the-badge
[stars-url]: https://github.com/PizzaMarinara/MaximumWeightedMatching/stargazers
[issues-shield]: https://img.shields.io/github/issues/PizzaMarinara/MaximumWeightedMatching.svg?style=for-the-badge
[issues-url]: https://github.com/PizzaMarinara/MaximumWeightedMatching/issues
[license-shield]: https://img.shields.io/github/license/PizzaMarinara/MaximumWeightedMatching.svg?style=for-the-badge
[license-url]: https://github.com/PizzaMarinara/MaximumWeightedMatching/blob/master/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/e-fantini/
[twitter-shield]: https://img.shields.io/badge/-Twitter-black.svg?style=for-the-badge&logo=twitter&colorB=555
[twitter-url]: https://twitter.com/Pizza_Marinara
