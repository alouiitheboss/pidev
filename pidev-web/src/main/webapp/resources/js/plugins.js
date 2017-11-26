$(document).ready(function () {
    "use strict";
    $(window).resize(function () {
        $(".All").each(function () {
            $(this).css('marginTop', ($(window).height() - $(this).height()) / 2);
            
            
           
        });
        $(".All .content div").each(function () {
            $(this).css('marginTop', ($(".All").height() - $(this).height()) / 2);
        });
    });

    $(".All").each(function () {
        $(this).css('marginTop', ($(window).height() - $(this).height()) / 2);
    });
    $(".All .content div").each(function () {
        $(this).css('marginTop', ($(".All").height() - $(this).height()) / 2);
    });
    $(".All .content .sign-in button").click(function () {
        $(".signover .first").fadeOut(200, function () {
            $(".signover .second").fadeIn(200);
        })
        $(".signover").animate({
            left: $(".All .content .sign-in").offset().left
        }, 500)
        $(".All .content .sign-up button").click(function () {
            $(".signover .second").fadeOut(200, function () {
                $(".signover .first").fadeIn(200);
            })
            $(".signover").animate({
                left: $(".All .content .sign-up").offset().left
            }, 500);
        });
    });
});