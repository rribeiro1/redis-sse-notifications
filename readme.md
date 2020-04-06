# Redis Pub/Sub Demo

It has 2 demo users. `john` and `jack`

# Run Redis on localhost

```bash
redis-cli PUBLISH demo.notifications '{"userId": "john", "message": "notes"}'
redis-cli PUBLISH demo.notifications '{"userId": "jack", "message": "notes"}'
```

# Run demo.html

```bash
python -m SimpleHTTPServer 8000
```

visit `http://localhost:8000/demo.html`

# Run the app as a normal spring boot app

confirm jack only receive when userId == jack
confirm john only receive when userId == john