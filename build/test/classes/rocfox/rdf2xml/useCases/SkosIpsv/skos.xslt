<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:skos="http://www.w3.org/2004/02/skos/core#"
	xml:base="http://www.esd.org.uk/standards/ipsv/2.00/ipsv-schema#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:esd="http://www.esd.org.uk/standards">
	<xsl:output method="xml" indent="yes" encoding="utf-8" standalone="yes"/>
	<xsl:variable name="ItemName" select="translate(/esd:ControlledList/@ItemName, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
	<xsl:variable name="URI">
		<xsl:text>http://www.esd.org.uk/standards/</xsl:text>
		<xsl:value-of select="$ItemName"/>
		<xsl:text>/</xsl:text>
		<xsl:value-of select="/esd:ControlledList/@Version"/>
		<xsl:text>/</xsl:text>
		<xsl:value-of select="$ItemName"/>
		<xsl:text>-schema#</xsl:text>
	</xsl:variable>
	<xsl:template match="esd:ControlledList">
		<rdf:RDF xml:base="{$URI}">
			<skos:ConceptScheme rdf:about="{$URI}">
				<rdfs:label>
					<xsl:value-of select="@ItemName"/>
				</rdfs:label>
				<dc:title>
					<xsl:value-of select="@ListName"/>
				</dc:title>
				<dc:description>
					<xsl:value-of select="esd:Metadata/esd:Description"/>
				</dc:description>
				<xsl:for-each select="/esd:ControlledList/esd:Item[(@Preferred = 'true') and (count(esd:BroaderItem) = 0)]">
					<skos:hasTopConcept rdf:resource="{$URI}Concept{@ConceptId}"/>
				</xsl:for-each>
			</skos:ConceptScheme>
			<xsl:apply-templates select="esd:Item[@Preferred = 'true']" mode="Concept"/>
		</rdf:RDF>
	</xsl:template>
	<xsl:template match="esd:Item" mode="Concept">
		<skos:Concept rdf:ID="Concept{@ConceptId}">
			<skos:inScheme rdf:resource="{$URI}"/>
			<xsl:apply-templates select="/esd:ControlledList/esd:Item[@ConceptId = current()/@ConceptId]" mode="Term"/>
			<xsl:apply-templates select="." mode="Broader"/>
			<xsl:apply-templates select="." mode="Narrower"/>
			<xsl:apply-templates select="." mode="Related"/>
		</skos:Concept>
	</xsl:template>
	<xsl:template match="esd:Item" mode="Term">
		<xsl:choose>
			<xsl:when test="@Preferred = 'true'">
				<skos:prefLabel>
					<xsl:value-of select="esd:Name"/>
				</skos:prefLabel>
			</xsl:when>
			<xsl:otherwise>
				<skos:altLabel>
					<xsl:value-of select="esd:Name"/>
				</skos:altLabel>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="esd:Item" mode="Broader">
		<xsl:for-each select="/esd:ControlledList/esd:Item[(@Preferred = 'true') and (@ConceptId = current()/esd:BroaderItem/@ConceptId)]">
			<skos:broader rdf:resource="{$URI}Concept{@ConceptId}"/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="esd:Item" mode="Related">
		<xsl:for-each select="/esd:ControlledList/esd:Item[(@Preferred = 'true') and (@ConceptId = current()/esd:RelatedItem/@ConceptId)]">
			<skos:related rdf:resource="{$URI}Concept{@ConceptId}"/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="esd:Item" mode="Narrower">
		<xsl:for-each select="/esd:ControlledList/esd:Item[(@Preferred = 'true') and (esd:BroaderItem/@ConceptId = current()/@ConceptId)]">
			<skos:narrower rdf:resource="{$URI}Concept{@ConceptId}"/>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>