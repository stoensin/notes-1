---------------------------
GET							|
---------------------------
	# �������͵�����ʽ
		* ��Ӧ ResponseEntity 
		* ���Ի�ȡ����Ӧͷ,״̬�����Ϣ

			<T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)
			<T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException;
			<T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;

		
		* ��Ӧ����,ֱ�ӷ��ر����Ķ���
			<T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException;
			<T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
			<T> T getForObject(URI url, Class<T> responseType) throws RestClientException;

	
	# ����ʹ��ռλ�������ò�ѯ����
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost?name={1}&age={2}",String.class,"KevinBlandy","23");
	
	
	# ����ʹ�� UriComponentsBuilder ������ URI ����
	