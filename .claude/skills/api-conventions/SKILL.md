---
name: api-conventions
description: REST API design, input DTO and response conventions for this project. Use when creating or modifying controllers, endpoints, input records or exception handling.
---

# API conventions

## Richardson maturity
Level 2: resource URIs + correct HTTP verbs + correct status codes.
Do NOT implement HATEOAS. No links in responses, no EntityModel,
no spring-hateoas dependency.

## URIs
- Plural nouns: `/books`, `/books/{id}`
- No verbs in the URI.

## Controller mapping
The base URI of the resource is declared ONCE, in a class-level
`@RequestMapping`. Method annotations carry only the complement.
Never repeat the base URI in the methods.

```java
@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public ResponseEntity<List<BookModel>> getAllBooks() { }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneBook(@PathVariable(value = "id") UUID id) { }

    @PostMapping
    public ResponseEntity<BookModel> saveBook(@RequestBody @Valid BookRecordDto bookRecordDto) { }
}
```

- Collection endpoints (list all, create) take a bare `@GetMapping` /
  `@PostMapping` with no path at all.
- Item endpoints take only `"/{id}"`.
- When the method annotation also needs another attribute, the path stops
  using the shortcut form and becomes explicit:
  `@GetMapping(value = "/{id}", version = "1")`.

## Verbs and status codes
| Action | Verb | Path | Success   |
|---|---|---|-----------|
| List all | GET | `/books` | 200       |
| Get one | GET | `/books/{id}` | 200 / 404 |
| Create | POST | `/books` | 201       |
| Update | PUT | `/books/{id}` | 200 / 404 |
| Delete | DELETE | `/books/{id}` | 200 / 404 |
| Validation failure | — | — | 400       |

## Input DTOs
- Java records, named `<Name>RecordDto`.
- Carry Bean Validation annotations.

## Responses
- There are NO output DTOs. Return the entity.
- Controllers return `ResponseEntity<T>` and set the status explicitly
  with `ResponseEntity.status(HttpStatus.X).body(...)`.
- Do not use by try/catch in the controller.
- Errors will be addressed later upon request.