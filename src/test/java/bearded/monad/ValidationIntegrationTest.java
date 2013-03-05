package bearded.monad;

import bearded.monad.Validation;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class ValidationIntegrationTest {

    @Test
    public void should_3() throws Exception {
        Map<String, Map<String, Map<String, Integer>>> continents = buildMap();

        Validation<Integer, String> populationParis = getFrom(continents, "Europe")
                .flatMap(continent -> getFrom(continent, "France")
                        .flatMap(country -> getFrom(country, "Paris")
                                .map(population -> population)));

        assertThat(populationParis.get()).isEqualTo(2_234_105);
    }

    private <K, V> Validation<V, String> getFrom(Map<K, V> map, K key) {
        if (!map.containsKey(key)) {
            return Validation.Failure("unknown key: '" + key + "'");
        }

        return Validation.Success(map.get(key));
    }

    private Map<String, Map<String, Map<String, Integer>>> buildMap() {
        Map<String, Map<String, Map<String, Integer>>> continents = Maps.newHashMap();
        Map<String, Map<String, Integer>> countries;

        countries = Maps.newHashMap();
        countries.put("France", getCities("Paris:2234105,Nantes:282047,Lyon:479803"));
        countries.put("Allemagne", getCities("Berlin:3515473,Francfort-sur-le-Main:700000,Munich:1353192"));
        countries.put("Italie", getCities("Rome:2783300,Milan:1324110,Naples:956739"));
        continents.put("Europe", countries);

        countries = Maps.newHashMap();
        countries.put("Etats-Unis", getCities("Washington:601723,New York:8175133,San Francisco:805235"));
        continents.put("Amérique", countries);

        countries = Maps.newHashMap();
        countries.put("Chine", getCities("Pekin:19610000,Shanghai:23019148,Nankin:8004700"));
        continents.put("Asie", countries);

        return continents;
    }

    private Map<String, Integer> getCities(String cityDescription) {
        Splitter.MapSplitter mapSplitter = Splitter.on(",").withKeyValueSeparator(":");
        Map<String, String> map = mapSplitter.split(cityDescription);
        Map<String, Integer> cities = Maps.newHashMap();
        for (String city : map.keySet()) {
            cities.put(city, Integer.valueOf(map.get(city)));
        }
        return cities;
    }
}
