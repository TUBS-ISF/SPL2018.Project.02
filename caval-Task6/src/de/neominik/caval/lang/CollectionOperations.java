package de.neominik.caval.lang;

import de.neominik.caval.lang.collections.Collection;
import de.neominik.caval.lang.collections.PersistentList;

@SuppressWarnings("unchecked")
public abstract class CollectionOperations {
	public static class Conjoin extends AFn {
		@Override
		public Object invoke() {
			return PersistentList.of();
		}

		@Override
		public Object invoke(Object coll) {
			return coll;
		}

		@Override
		public Object invoke(Object coll, Object x) {
			return ((Collection) coll).conj(x);
		}

		@Override
		public Object invoke(Object coll, Object arg2, Object arg3) {
			return ((Collection) coll).conj(arg2).conj(arg3);
		}

		@Override
		public Object invoke(Object coll, Object arg2, Object arg3, Object arg4) {
			return ((Collection) coll).conj(arg2).conj(arg3).conj(arg4);
		}

		@Override
		public Object invoke(Object coll, Object arg2, Object arg3, Object arg4, Object arg5) {
			return ((Collection) coll).conj(arg2).conj(arg3).conj(arg4).conj(arg5);
		}

		@Override
		public Object invoke(Object coll, Object arg2, Object arg3, Object arg4, Object arg5, Object... args) {
			Collection collection = (Collection) coll;
			collection = collection.conj(arg2).conj(arg3).conj(arg4).conj(arg5);
			for (Object arg : args) {
				collection = collection.conj(arg);
			}
			return collection;
		}
	}

	public static class First extends AFn {
		@Override
		public Object invoke(Object coll) {
			if (coll == null)
				return null;
			if (coll instanceof String)
				return ((String) coll).substring(0, 1);
			Collection collection = (Collection) coll;
			return collection.first();
		}
	}

	public static class Rest extends AFn {
		@Override
		public Object invoke(Object coll) {
			if (coll == null)
				return PersistentList.of();
			if (coll instanceof String)
				return ((String) coll).substring(1);
			Collection collection = (Collection) coll;
			return collection.rest();
		}
	}

	public static class Count extends AFn {
		@Override
		public Object invoke(Object coll) {
			if (coll == null)
				return 0;
			if (coll instanceof String)
				return ((String) coll).length();
			Collection collection = (Collection) coll;
			return collection.count();
		}
	}
}
