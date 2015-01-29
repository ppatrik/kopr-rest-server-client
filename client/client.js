var client = function () {
    function postCreate() {
        var selector = "#postCreate";

        function getDataInJson() {
            function parse(s) {
                s = s.replace(/\"/g, "\\\"");
                s = s.replace(/%/g, "%%");
                s = s.replace(/\n/g, "\\n");
                return s;
            }

            var data = "";
            data += "\"nazov\": \"" + parse($("#nazov").val()) + "\", ";
            data += "\"autor\": \"" + parse($("#autor").val()) + "\", ";
            data += "\"jazyk\": \"" + parse($("#jazyk").val()) + "\", ";
            data += "\"text\": \"" + parse($("#text").val()) + "\", ";
            data += "\"kluc\": \"" + parse($("#kluc").val()) + "\", ";
            data += "\"data\": \"" + parse($("#data").val()) + "\" ";
            return "{ " + data + "}";
        }

        var xml = new XMLHttpRequest();
        xml.open("POST", "/dokument");
        xml.setRequestHeader("Content-Type", "application/json");

        if (xml.overrideMimeType) {
            xml.setRequestHeader("Connection", "close");
        }

        var loadInt = loader(selector);
        xml.onreadystatechange = function (r) {
            if (xml.readyState == 4) {
                clearInterval(loadInt);

                var json = $.parseJSON(xml.response);
                if (json.status == 200) {
                    success(selector, "Uložené pod UUID '" + json.data + "'");
                    getList();
                    getMeta(json.data);
                    getData(json.data);
                } else {
                    error(selector, json.text)
                }

                xml = null;
            }
        };

        xml.send(getDataInJson());
    }

    function getList() {
        var selector = "#getList";

        function putData(data) {
            function parseRow(row) {
                var r = "<tr data-id=\"" + row.uuid + "\">";
                r += "<td>" + row.uuid + "</td>";
                r += "<td>" + row.nazov + "</td>";
                r += "<td>" + row.autor + "</td>";
                r += "<td>" + row.jazyk + "</td>";
                r += "<td>" + row.text + "</td>";
                r += "<td>" + row.kluc + "</td>";
                r += "<td><a class='podobnost'>{%}</a> <a class='meta'>{meta}</a> <a class='data'>{data}</a></td>";
                return r + "</tr>";
            }

            var tbody = $(selector).find("tbody");
            tbody.html("");
            for (var i in data) {
                var row = data[i];
                tbody.append(parseRow(row));
            }
            function getUuid(obj) {
                return $(obj).parent().parent().attr("data-id");
            }

            tbody.find(".podobnost").click(function () {
                var limit = prompt("Zadajte prah", 10);
                getPodobnost(getUuid(this), limit);
            });
            tbody.find(".meta").click(function () {
                getMeta(getUuid(this));
            });
            tbody.find(".data").click(function () {
                getData(getUuid(this));
            });
        }

        var xml = new XMLHttpRequest();
        xml.open("GET", "/dokument");

        if (xml.overrideMimeType) {
            xml.setRequestHeader("Connection", "close");
        }

        var loadInt = loader(selector);
        xml.onreadystatechange = function (r) {
            if (xml.readyState == 4) {
                clearInterval(loadInt);

                var json = $.parseJSON(xml.response);
                if (json.status == 200) {
                    clearStatus(selector);
                    putData(json.data);
                } else {
                    error(selector, json.text)
                }

                xml = null;
            }
        };

        xml.send();
    }

    function getMeta(uuid) {
        var selector = "#getMeta";
        var xml = new XMLHttpRequest();
        xml.open("GET", "/dokument/" + uuid);

        if (xml.overrideMimeType) {
            xml.setRequestHeader("Connection", "close");
        }

        var loadInt = loader(selector);
        xml.onreadystatechange = function (r) {
            if (xml.readyState == 4) {
                clearInterval(loadInt);

                var json = $.parseJSON(xml.response);
                if (json.status == 200) {
                    clearStatus(selector);
                    $("#meta_nazov").val(json.data.nazov);
                    $("#meta_autor").val(json.data.autor);
                    $("#meta_jazyk").val(json.data.jazyk);
                    $("#meta_text").val(json.data.text);
                    $("#meta_kluc").val(json.data.kluc);
                    success(selector, "Načítané UUID '" + uuid + "'");
                } else {
                    error(selector, json.text)
                }

                xml = null;
            }
        };

        xml.send();
    }

    function getData(uuid) {
        var selector = "#getData";
        var xml = new XMLHttpRequest();
        xml.open("GET", "/dokument/" + uuid + "/data");

        if (xml.overrideMimeType) {
            xml.setRequestHeader("Connection", "close");
        }

        var loadInt = loader(selector);
        xml.onreadystatechange = function (r) {
            if (xml.readyState == 4) {
                clearInterval(loadInt);

                var json = $.parseJSON(xml.response);
                if (json.status == 200) {
                    clearStatus(selector);
                    $("#meta_data").val(json.data);
                    success(selector, "Načítané UUID '" + uuid + "'");
                } else {
                    error(selector, json.text)
                }

                xml = null;
            }
        };

        xml.send();
    }

    function getPodobnost(uuid, limit) {
        var selector = "#getPodobnost";

        function putData(data) {
            function parseRow(row) {
                var r = "<tr data-id=\"" + row.uuid + "\">";
                r += "<td>" + row.uuid + "</td>";
                r += "<td>" + row.podobnost + "</td>";
                r += "<td><a class='meta'>{meta}</a> <a class='data'>{data}</a></td>";
                return r + "</tr>";
            }

            var tbody = $(selector).find("tbody");
            tbody.html("");
            for (var i in data) {
                var row = data[i];
                tbody.append(parseRow(row));
            }
            function getUuid(obj) {
                return $(obj).parent().parent().attr("data-id");
            }

            tbody.find(".meta").click(function () {
                getMeta(getUuid(this));
            });
            tbody.find(".data").click(function () {
                getData(getUuid(this));
            });
        }

        var xml = new XMLHttpRequest();
        xml.open("GET", "/dokument/" + uuid + "/" + limit);

        if (xml.overrideMimeType) {
            xml.setRequestHeader("Connection", "close");
        }

        var loadInt = loader(selector);
        xml.onreadystatechange = function (r) {
            if (xml.readyState == 4) {
                clearInterval(loadInt);

                var json = $.parseJSON(xml.response);
                if (json.status == 200) {
                    clearStatus(selector);
                    $("#prah").val(limit);
                    putData(json.data);
                } else {
                    error(selector, json.text)
                }

                xml = null;
            }
        };

        xml.send();
    }

    /** globals **/

    function dots(i) {
        if (i == 0)
            return ".";
        if (i == 1)
            return "..";
        return "...";
    }

    function loading(selector, i) {
        $(selector).find(".status").html("<span class=\"loading\">Načítavam" + dots(i % 3) + "</span>");
    }

    function success(selector, message) {
        $(selector).find(".status").html("<span class=\"ok\">" + message + "</span>");
    }

    function clearStatus(selector) {
        $(selector).find(".status").html("");
    }

    function error(selector, message) {
        $(selector).find(".status").html("<span class=\"error\">" + message + "</span>");
    }

    function loader(selector) {
        var i = 0;
        loading(selector, i++);
        return setInterval(function () {
            loading(selector, i++);
        }, 200)
    }

    function init() {
        $("#postCreate").find("button").click(postCreate);
        $("#getList").find("legend").click(getList);
    }

    init();
    getList();
};