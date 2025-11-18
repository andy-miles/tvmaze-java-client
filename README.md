<a name="readme-top"></a>
<!-- Template Credit: Othneil Drew (https://github.com/othneildrew),
                      https://github.com/othneildrew/Best-README-Template/tree/master -->
<!-- PROJECT SHIELDS -->
<div align="center">

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

</div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://www.tvmaze.com/">
    <img alt="The TVDB Logo" src="https://static.tvmaze.com/images/tvm-header-logo.png">
  </a>
  <br/>
  <a href="https://www.tvmaze.com/">tvmaze.com</a>
  <h3 align="center">tvmaze-java-client</h3>

  <p align="center">
    A client to access the TVMaze API
    <br />
    <a href="https://www.amilesend.com/tvmaze-java-client"><strong>Maven Project Info</strong></a>
    -
    <a href="https://www.amilesend.com/tvmaze-java-client/apidocs/index.html"><strong>Javadoc</strong></a>
    <br />
    <a href="https://github.com/andy-miles/tvmaze-java-client/issues">Report Bug</a>
    -
    <a href="https://github.com/andy-miles/tvmaze-java-client/issues">Request Feature</a>
  </p>
</div>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#recipes">Recipes</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
# About The Project

An easy-to-use java client to access the TVMaze API for programmatic access to TV show, episode and cast/crew information.

## Getting Started

Include this package as a dependency in your project. Note: This package is published to both
[GitHub](https://github.com/andy-miles/tvmaze-java-client/packages/2112748) and Maven Central repositories.

```xml
<dependency>
    <groupId>com.amilesend</groupId>
    <artifactId>tvmaze-java-client</artifactId>
    <version>2.2.2</version>
</dependency>
```

## Recipes

### Instantiation

Default:
```java
TvMaze client = new TvMaze();
SearchApi searchApi = client.getSearchApi();
List<ShowResult> results = searchApi.searchShows("friends");
```

With a RetryStrategy:
```java
TvMaze client = new TvMaze(new DefaultConnectionBuilder()
        .httpClient(new OkHttpClient())
        .baseUrl(TvMaze.API_URL)
        .userAgent(TvMaze.USER_AGENT)
        .authManager(new NoOpAuthManager())
        .gsonFactory(new GsonFactory())
        .isGzipContentEncodingEnabled(true)
        // Options are ExponentialDelayRetryStrategy, FixedDelayRetryStrategy
        // or NoRetryStrategy (default).
        .retryStrategy(ExponentialDelayRetryStrategy.builder()
                .baseDelayMs(500L)
                .maxJitterMs(100L)
                .maxAttempts(3)
                .maxTotalDelayMs(2000L)
                .build())
        .build());

```
### Customizing the HTTP client configuration

<details>
<summary>OkHttpClientBuilder example</summary>

If your use-case requires configuring the underlying <code>OkHttpClient</code> instance (e.g., configuring your own
SSL cert verification, proxy, and/or connection timeouts), you can configure the client with the provided
[OkHttpClientBuilder](https://github.com/andy-miles/tvmaze-java-client/blob/main/src/main/java/com/amilesend/tvmaze/client/connection/http/OkHttpClientBuilder.java),
or alternatively with [OkHttp's builder](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/).

```java
OkHttpClient httpClient = OkHttpClientBuilder.builder()
        .trustManager(myX509TrustManager) // Custom trust manager for self/internally signed SSL/TLS certs
        .hostnameVerifier(myHostnameVerifier) // Custom hostname verification for SSL/TLS endpoints
        .addInterceptor(myInterceptor) // Custom okhttp interceptor (e.g., logging)
        .proxy(myProxy, myProxyUsername, myProxyPassword) // Proxy config
        .connectTimeout(8000L) // connection timeout in milliseconds
        .readTimeout(5000L) // read timeout in milliseconds
        .writeTimeout(5000L) // write timeout in milliseconds
        .build();
Connection connection = Connection.builder()
        .httpClient(httpClient)
        .gsonFactory(GsonFactory.getInstance())
        .build();
TvMaze client = new TvMaze(connection);
```

</details>

## Contributing

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<div align="right">(<a href="#readme-top">back to top</a>)</div>

<!-- LICENSE -->
## License

Distributed under the GPLv3 license. See [LICENSE](https://github.com/andy-miles/tvmaze-java-client/blob/main/LICENSE) for more information.

<div align="right">(<a href="#readme-top">back to top</a>)</div>


<!-- CONTACT -->
## Contact

Andy Miles - andy.miles (at) amilesend.com

Project Link: [https://github.com/andy-miles/tvmaze-java-client](https://github.com/andy-miles/tvmaze-java-client)

<div align="right">(<a href="#readme-top">back to top</a>)</div>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/andy-miles/tvmaze-java-client.svg?style=for-the-badge
[contributors-url]: https://github.com/andy-miles/tvmaze-java-client/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/andy-miles/tvmaze-java-client.svg?style=for-the-badge
[forks-url]: https://github.com/andy-miles/tvmaze-java-client/network/members
[stars-shield]: https://img.shields.io/github/stars/andy-miles/tvmaze-java-client.svg?style=for-the-badge
[stars-url]: https://github.com/andy-miles/tvmaze-java-client/stargazers
[issues-shield]: https://img.shields.io/github/issues/andy-miles/tvmaze-java-client.svg?style=for-the-badge
[issues-url]: https://github.com/andy-miles/tvmaze-java-client/issues
[license-shield]: https://img.shields.io/github/license/andy-miles/tvmaze-java-client.svg?style=for-the-badge
[license-url]: https://github.com/andy-miles/tvmaze-java-client/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/andy-miles
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
