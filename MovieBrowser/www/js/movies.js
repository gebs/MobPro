function SearchMovie() {
    var searchval = htmlEncode($("#txtSearch").val());
    $.get("http://www.omdbapi.com/?y=&plot=short&r=json&t=" + searchval, function (data) {
        if (data != undefined && data.Response != "False") {
            fillMovie(data);
        }else{
            $("#searchErrorText").show();
        }
    });

}
fillFavorites();
$("#btnSearch").click(function () {
    $("#searchErrorText").hide();
    SearchMovie();
});
function fillFavorites() {
    $("#favList").empty();
    $.each(favorites, function (i, a) {
        $("#favList").append("<li><a id=\"fav_" + i + "\">" + a.Title + "</a></li>")
        $("#fav_" + i).click(function () {
            fillFavorite(i);
        });
    });
}
function fillFavorite(id) {
    fillMovie(favorites[id]);
}
function fillMovie(movie) {
    $("#txtTitle").val();
    $("#txtYear").val();
    $("#txtActors").empty();
    $("#txtPlot").empty();
    $("#txtRating").val();

    $("#txtTitle").val(movie.Title);
    $("#txtYear").val(movie.Year);
    $("#txtActors").val(movie.Actors);
    $("#txtPlot").val(movie.Plot);
    $("#txtRating").val(movie.Ratings[0].Value);
    $("#imgPoster").attr("src", movie.Poster);

    $("body").pagecontainer("change", "#page-movie", { transition: "flip" });

}
function htmlEncode(value) {
    //create a in-memory div, set it's inner text(which jQuery automatically encodes)
    //then grab the encoded contents back out.  The div never exists on the page.
    return $('<div/>').text(value).html();
}