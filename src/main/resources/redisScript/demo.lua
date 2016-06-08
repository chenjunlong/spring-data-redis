if redis.call('GET', KEYS[1]) == false
then redis.call('SET', KEYS[1], ARGV[1])
end
if redis.call('GET', KEYS[2]) == false
then redis.call('SET', KEYS[2], ARGV[2])
end
return "ok"