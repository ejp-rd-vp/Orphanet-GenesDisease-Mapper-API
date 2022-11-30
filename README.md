# Orphanet-GenesDisease-Mapper-API
This API focuses on Diseases and  Genes related to them, Orphanet proposes a link between genes and diseases related to them and this API implements that.

## Gene to disease(s)
The service allows query based on HGNC codes or gene symbol

### API parameters
'gendis/find?by={hgnc code or Symbol}&input={the value of hgnc code or Symbol}

ex: [http://purl.org/gendis/find?by=hgnc&input=3603]

ex: [http://purl.org/gendis/find?by=symbol&input=FBN1] 

You will obtain the list of diseases linked to the specific gene (by code or symbol)

Returns Orphacodes and label:
* orphaCode

> "orphaCode":
>
> "http://www.orpha.net/ORDO/Orphanet_284963",
> "label":"Marfan syndrome type 1"

* "inferredResourceResponses": 

In Orphanet Knowledge base, genes are linked to specific entities such as "subtype of disease". For instance, FBN1 gene will be linked to Marfan type 1 and TGFBR2 gene will be linked to Marfan type 2. However, resources are often annotated at the level of "disorder" clinical entities. (Marfan syndrome in this example)

The API will return a result expanded to this "upper" level (from subtype to disorder level) when relevant as "inferredResourceResponses" :

> "inferredResourceResponses":
>
> {"orphaCode":"http://www.orpha.net/ORDO/Orphanet_558",
> "label":"Marfan syndrome"}

* "orphanetEndpoint"

We added a feature that allows to query the Orphanet Resources Endpoint (Biobanks and Registries) based on the returned list of Orphacodes
> "orphanetEndpoint": "http://155.133.131.171:8080/Orphanet/resource/search?code=300382&code=91387&code=2623&code=2833&code=284963&code=284979&code=3449&code=969&code=1885&code=558"

## Disease (orphacode) to Genes
This service allows query based on Orphacodes to retrieve genes linked, with HGCN code and Symbol

### API parameters

'gendis/find?by=orphacode&input={orphacode or IRI}

ex: [http://155.133.131.171:8080/GENES/gendis/find?by=orphacode&input=http://www.orpha.net/ORDO/Orphanet_284963]

ex: [http://155.133.131.171:8080/GENES/gendis/find?by=orphacode&input=284963]

You will obtain the list of genes linked to the clinical entity orphacode concept, with HGNC code, symbol and "relation" between orphacode concept and each gene.

> directLinkedResponses	
> hgnc	"3603"
> symbol	"FBN1"
> relation	"Disease-causing germline mutation(s) in"
> inferredResponses	[]
> apiVersion	"v0.1"

* directLinkedResponses : the clinical entity concept (orphacode) is annotated with the gene in the Orphanet knowledge base
* inferredResponses : the clinical entity concept (orphacode) is not directly annotated with the gene but a subtype of the entity is.

Example: FBN1 gene is linked to Marfan type 1. TGFBR2 gene is linked to Marfan type 2. 
The Marfan syndrome (orpha:558) is not linked directly to any gene, but has "marfan type 1" and "marfan type 2" as subtypes. Therefore, in order to obtain genes related to "Marfan Syndrome" we propose an inferred response including subtypes.

ex: [http://155.133.131.171:8080/GENES/gendis/find?by=orphacode&input=558]

> "inferredResponses"	
>	
> "orphaCode":	"284963"
> 
> "label":	"Marfan syndrome type 1"
> 
  > linkedResponses
  >  
  > "hgnc":"3603",
  > "symbol":"FBN1",
  > "relation":	"Disease-causing germline mutation(s) in"
>	
> "orphaCode":"284973"
> 
> "label": "Marfan syndrome type 2"
> 
  > linkedResponses
  > 	
  > "hgnc":	"11773",
  > "symbol":	"TGFBR2",
  > "relation":	"Disease-causing germline mutation(s) in"
