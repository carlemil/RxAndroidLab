# RxAndroidLab

1. Make days into an observable and show the list using the observable.
http://reactivex.io/documentation/operators/from.html
https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava

2. Pick the "temp" field from each day object (use map).
http://reactivex.io/documentation/operators/map.html

3. Filter on temp > 20, only days with a temp over 20 should be visible.
http://reactivex.io/documentation/operators/filter.html


4. Fetch the data from github rather than using a local string.
https://square.github.io/retrofit/
And the json file can be found online at:
https://raw.githubusercontent.com/carlemil/RxAndroidLab/master/weather.json
The observable will now contain a array, just like before we will need to unwrap the array into a stream. This can be done using a operator called flatmap and the .from operator.

5. Bind the button onclicklistener using Rx, only perform the fetch of the above json when the button is pressed.
https://github.com/JakeWharton/RxBinding

6. Prevent multiple requests due to double/multiple clicks on the button.
http://reactivex.io/documentation/operators.html#tree go down to "I want to reemit only certain items from an Observable" and see what operator might help us out with this.

7. Error handling - show the Snackbar if something goes wrong (test this by changing the url to a non valid url.
http://www.androidhive.info/2015/09/android-material-design-snackbar-example/

8. Do some cool stuff with Rx, try email validation using 2 edittexts for example.
