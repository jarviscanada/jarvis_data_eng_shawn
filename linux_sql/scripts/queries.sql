SELECT cpu_number,id AS host_id,total_mem AS "total_mem(x100MB)"
FROM host_info
GROUP BY host_id
ORDER BY cpu_number ASC;



SELECT t,idd,name,avg(usage)
from (
	SELECT date_trunc('minute',host_usage.timestamp) AS t ,host_info.id AS idd,host_info.hostname AS name,(host_info.total_mem-memory_free)/host_info.total_mem AS usage
	FROM host_usage
	INNER JOIN host_info ON (host_info.id=host_usage.host_id) 
) as foo
WHERE extract(minutes from t)::int %5 =0
GROUP BY foo.t,foo.idd,foo.name;
