package ru.kraldraav.autopartsfinder.services.sources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.kraldraav.autopartsfinder.models.Autopart;
import ru.kraldraav.autopartsfinder.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Log4j2
public class ExistAutopartCatalogServiceImpl implements AutopartsCatalogService {
    private final String requestUrl = "https://exist.ru/Price/?pcode=%s";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Autopart> getAutopartByArticle(String productArticle) {

        var autoparts = new ArrayList<Autopart>();
        var document = HttpUtils.getHtmlDocument(requestUrl.formatted(productArticle));
        var scripts = document.select("script");

        var cdata = scripts
                .stream()
                .map(x -> x.childNodes())
                .flatMap(List::stream)
                .filter(x -> x.attributes().hasKey("data"))
                .filter(x -> x.attributes().get("data").contains("CDATA"))
                .findFirst();

        if (cdata.isEmpty())
            return null;

        var jsonData = cdata.get().attributes().get("data")
                .replace("\r\n", "")
                .replace("//<![CDATA[", "")
                .replace("var _data = ", "")
                .replace("; var _favs = [];", "")
                .replace("//]]>", "");

        try {
            JsonNode jsonRoot = objectMapper.readTree(jsonData);

            for (JsonNode jsonNode : jsonRoot) {
                try {
                    Iterator<JsonNode> prices = ((ArrayNode) jsonNode.get("AggregatedParts")).elements();

                    while (prices.hasNext()) {
                        var autopart = new Autopart();
                        autopart.Name = "%s %s".formatted(jsonNode.get("CatalogName").asText(), jsonNode.get("PartNumber").asText());
                        autopart.Description = jsonNode.get("Description").asText();
                        autopart.Price = prices.next().get("priceString").asText();
                        autoparts.add(autopart);
                    }
                } catch (Exception exception) {
                    log.error("Error while parsing prices! %s".formatted(exception.getMessage()));
                }
            }

        } catch (JsonProcessingException exception) {
            log.error("Error while processing JSON! %s".formatted(exception.getMessage()));
        }


        return autoparts;
    }
}
