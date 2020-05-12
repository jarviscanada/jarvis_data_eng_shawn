SELECT cpu_number,id AS host_id,total_mem AS "total_mem(x100MB)"
FROM host_info
GROUP BY host_id
ORDER BY cpu_number ASC;



select t,idd,name,avg(usage)
from (
	SELECT date_trunc('minute',host_usage.timestamp) as t ,host_info.id as idd,host_info.hostname as name,(host_info.total_mem-memory_free)/host_info.total_mem as usage
	FROM host_usage
	INNER JOIN host_info ON (host_info.id=host_usage.host_id) 
) as foo
where extract(minutes from t)::int %5 =0
group by foo.t,foo.idd,foo.name;
