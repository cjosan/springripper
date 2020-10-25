package screensaver;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class PeriodicalScopeConfigurer implements Scope {
	private Map<String, Map.Entry<LocalTime, Object>> map = new HashMap<>();

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		if (map.containsKey(name)) {
			Map.Entry<LocalTime, Object> pair = map.get(name);
			long secondsSinceLastRequest = Duration.between(pair.getKey(), LocalTime.now()).getSeconds();
			if (secondsSinceLastRequest > 3) {
				map.put(name, Map.entry(LocalTime.now(), objectFactory.getObject()));
			}
		} else {
			map.put(name, Map.entry(LocalTime.now(), objectFactory.getObject()));
		}

		return map.get(name).getValue();
	}

	@Override
	public Object remove(String s) {
		return null;
	}

	@Override
	public void registerDestructionCallback(String s, Runnable runnable) {

	}

	@Override
	public Object resolveContextualObject(String s) {
		return null;
	}

	@Override
	public String getConversationId() {
		return null;
	}
}
