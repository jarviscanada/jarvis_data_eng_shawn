SELECT cpu_number,id AS host_id,total_mem AS "total_mem(x100MB)"
FROM host_info
GROUP BY host_id
ORDER BY cpu_number ASC;
